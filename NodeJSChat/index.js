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
  getUserByRecieverEmail,
} = require("./helpers/users");

const server = http.createServer(app);
const io = socketio(server);

app.get("/hello", (req, res) => {
  res.json("I am user-service");
});

io.on("connect", (socket) => {
  socket.on("joinToChat", ({ name }, callback) => {
    try {
      const users = getUserByRecieverEmail(name);
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
      console.log(user);
      if (error) return callback(error);

      socket.join(user.room);

      callback();
    } catch (err) {
      console.log(err);
    }
  });

  socket.on("sendMessage", (message, callback) => {
    try {
      const user = getUserByRoomIdAndUsername(message.roomId, message.sender);
      io.to(message.recieverEmail).emit("joinToRoom", user);
      console.log("joinToRoom " + message.recieverEmail);
      console.log("emmiting to room " + message.roomId);
      setTimeout(() => {
        io.to(message.roomId).emit("message", {
          user: user.name,
          text: message.text,
          date: Date(Date.now()),
          roomId: message.roomId,
        });

        callback();
      }, 100);
    } catch (err) {
      console.log(err);
    }
  });

  socket.on("disconnectUser", (userInfo) => {
    try {
      console.log("User deleted" + removeUser(userInfo));
    } catch (e) {
      console.log(e);
    }
  });
});

server.listen(PORT, () => {
  console.log("user-service on 3000");
});

// eurekaHelper.registerWithEureka("chat", PORT);
