const http = require("http");
const express = require("express");
const app = express();
const socketio = require("socket.io");
const server = http.createServer(app);
const io = socketio(server);
const { addUser, deleteUser, userExists } = require("./userHelper");
let jwt = require("jsonwebtoken");

app.use(express.json());

var amqp = require("amqplib/callback_api");

var amqpConn = null;
const start = () => {
  amqp.connect(
    "amqp://dkvlkrcz:UMbH-DpXF4v-zN5qk8BxHgF1z1y-SsP6@roedeer.rmq.cloudamqp.com/dkvlkrcz" +
      "?heartbeat=60",
    (err, conn) => {
      if (err) {
        console.error("[AMQP]", err.message);
        return setTimeout(start, 1000);
      }
      conn.on("error", (err) => {
        if (err.message !== "Connection closing")
          console.error("[AMQP] conn error", err.message);
      });
      conn.on("close", function () {
        console.error("[AMQP] reconnecting");
        return setTimeout(start, 1000);
      });
      console.log("[AMQP] connected");
      amqpConn = conn;
      whenConnected();
    }
  );
};

function whenConnected() {
  startPublisher();
  startWorker();
}

var pubChannel = null;
var offlinePubQueue = [];
function startPublisher() {
  amqpConn.createConfirmChannel((err, ch) => {
    if (closeOnErr(err)) return;
    ch.on("error", (err) => {
      console.error("[AMQP] channel error", err.message);
    });
    ch.on("close", () => {
      console.log("[AMQP] channel closed");
    });

    pubChannel = ch;
    while (true) {
      var m = offlinePubQueue.shift();
      if (!m) break;
      publish(m[0], m[1], m[2]);
    }
  });
}

const publish = (exchange, routingKey, content) => {
  try {
    pubChannel.publish(
      exchange,
      routingKey,
      content,
      { persistent: true },
      function (err, ok) {
        if (err) {
          console.error("[AMQP] publish", err);
          offlinePubQueue.push([exchange, routingKey, content]);
          pubChannel.connection.close();
        }
      }
    );
  } catch (e) {
    console.error("[AMQP] publish", e.message);
    offlinePubQueue.push([exchange, routingKey, content]);
  }
};
// A worker that acks messages only if processed succesfully

function startWorker() {
  amqpConn.createChannel((err, ch) => {
    if (closeOnErr(err)) return;
    ch.on("error", function (err) {
      console.error("[AMQP] channel error", err.message);
    });

    ch.on("close", function () {
      console.log("[AMQP] channel closed");
    });

    ch.prefetch(10);
    ch.assertQueue("location", { durable: true }, function (err, _ok) {
      if (closeOnErr(err)) return;
      ch.consume("location", processMsg, { noAck: false });
      console.log("Worker is started");
    });

    function processMsg(msg) {
      work(msg, function (ok) {
        try {
          if (ok) ch.ack(msg);
          else ch.reject(msg, true);
        } catch (e) {
          closeOnErr(e);
        }
      });
    }
  });
}

const work = async (msg, cb) => {
  const data = JSON.parse(msg.content);
  console.log(data);

  emitlocation(data);
  cb(true);
};

function closeOnErr(err) {
  if (!err) return false;
  console.error("[AMQP] error", err);
  amqpConn.close();
  return true;
}

const {
  getNextRoute,
  isEndOfRoute,
  resetCounter,
  reverseRoute,
} = require("./fakeRoutes");
var intervalId = null;
androidApp = (data) => {
  console.log(intervalId);
  if (isEndOfRoute()) {
    intervalId = null;
    reverseRoute();
    resetCounter();
  }

  if (intervalId == null)
    intervalId = setInterval(() => {
      if (isEndOfRoute()) clearInterval(intervalId);

      !isEndOfRoute() &&
        publish(
          "",
          "location",
          Buffer.from(
            JSON.stringify({
              agentUsername: data.agentUsername,
              coordinates: getNextRoute(),
            })
          )
        );
    }, 450);
};

io.on("connect", (socket) => {
  socket.on("location", (data, callback) => {
    try {
      const decoded = jwt.decode(data.carToken);

      if (decoded) {
        if (decoded.ownerUsername !== data.agentUsername)
          throw new Error("Unauthorized");

        if (!userExists(data.agentUsername)) {
          socket.join(data.agentUsername);
          addUser(data.agentUsername);
        }
        androidApp(data);
      }
    } catch (err) {
      console.log(err);
    }
  });
});

const emitlocation = (data) => {
  io.sockets.emit(data.agentUsername, data.coordinates);
};

start();

server.listen(5000, () => {
  console.log("Started on 5000");
});
