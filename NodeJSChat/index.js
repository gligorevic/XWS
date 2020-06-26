const http = require("http");
const express = require("express");
const app = express();
const socketio = require("socket.io");
const PORT = process.env.PORT || 3200;
const eurekaHelper = require("./eurekaHelper");
const {
  addUser,
  removeUser,
  getUserByRoomIdAndUsername,
  removeAllUsersByName,
} = require("./helpers/users");
const cors = require("cors");

const server = http.createServer(app);
const io = socketio(server);
const { addMessage } = require("./handlers/messages");

app.use(express.json());

app.get("/hello", (req, res) => {
  res.json("I am user-service");
});

app.use("/message", require("./routes/messages"));

io.on("connect", (socket) => {
  socket.on("joinToChat", ({ name }, callback) => {
    try {
      socket.join(name);
      callback();
    } catch (err) {
      console.log(err);
    }
  });

  socket.on("join", ({ name, room, recieverEmail, chatName }, callback) => {
    try {
      const { error, user } = addUser({
        id: socket.id,
        name,
        room,
        recieverEmail,
        chatName,
      });

      if (error) return callback(error);

      socket.join(user.room);

      callback();
    } catch (err) {
      console.log(err);
    }
  });

  socket.on("sendMessage", async (message, callback) => {
    try {
      await addMessage(message);
      const user = getUserByRoomIdAndUsername(
        message.roomId,
        message.senderEmail
      );

      const reciever = getUserByRoomIdAndUsername(
        message.roomId,
        message.recieverEmail
      );

      if (reciever === undefined) {
        io.to(message.roomId).emit("message", {
          senderEmail: user.name,
          recieverEmail: message.recieverEmail,
          text: message.text,
          date: Date(Date.now()),
          roomId: message.roomId,
        });
        io.to(message.recieverEmail).emit("joinToRoom", user);
      } else
        io.to(message.roomId).emit("message", {
          senderEmail: user.name,
          recieverEmail: message.recieverEmail,
          text: message.text,
          date: Date(Date.now()),
          roomId: message.roomId,
        });

      callback();
    } catch (err) {
      console.log(err);
    }
  });

  socket.on("disconnectUser", (userInfo) => {
    try {
      console.log(userInfo);
      removeUser(userInfo);
    } catch (e) {
      console.log(e);
    }
  });

  socket.on("leaveChat", ({ name }) => {
    try {
      removeAllUsersByName(name);
      socket.disconnect();
    } catch (e) {
      console.log(e);
    }
  });
});

server.listen(PORT, () => {
  console.log("user-service on 3000");
});

eurekaHelper.registerWithEureka("chat", PORT);
