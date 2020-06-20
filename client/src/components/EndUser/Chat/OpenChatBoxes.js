import React, { useState, useEffect } from "react";
import Chat from "./Chat";
import { connect } from "react-redux";
import {
  setOpenChatBoxes,
  addOpenChatBox,
  appendMessageToRoom,
  resetUnreads,
} from "../../../store/actions/chat";
import io from "socket.io-client";

let socket;
function OpenChatBoxes({
  addOpenChatBox,
  setOpenChatBoxes,
  openChatBoxes,
  user,
  appendMessageToRoom,
  resetUnreads,
}) {
  useEffect(() => {
    socket = io("http://localhost:3200/");

    socket.emit("joinToChat", { name: user.username }, (error) => {
      console.log("joining rooms");
      if (error) {
        alert(error);
      }
    });

    socket.on("joinToRoom", (room) => {
      addOpenChatBox({
        roomId: room.room,
        chatName: room.chatName,
        sendTo: room.name,
        unreadedMessages: 0,
        messages: [],
      });
    });

    socket.on("message", (message) => {
      appendMessageToRoom(message, message.roomId);
    });
  }, []);

  const [exposedChats, setExposedChats] = useState([]);

  const exposeChat = (roomId) => {
    setExposedChats((old) => [...old, roomId]);
  };

  const collapseChat = (roomId) => {
    setExposedChats((oldEx) => oldEx.filter((rId) => rId !== roomId));
  };

  const removeFromOepnChatRooms = (box) => {
    setOpenChatBoxes(openChatBoxes.filter((o) => o.roomId !== box.roomId));
    setExposedChats((old) => old.filter((o) => o !== box.roomId));
  };

  return (
    <div
      style={{
        position: "fixed",
        bottom: 0,
        right: 0,
        display: "flex",
        alignItems: "flex-end",
        height: 310,
      }}
    >
      {openChatBoxes.map((o) => (
        <Chat
          roomId={o.roomId}
          chatName={o.chatName}
          exposed={exposedChats.indexOf(o.roomId) !== -1}
          exposeChat={() => exposeChat(o.roomId)}
          collapseChat={() => collapseChat(o.roomId)}
          closeChat={() => removeFromOepnChatRooms(o)}
          appendMessageToRoom={appendMessageToRoom}
          messages={o.messages}
          sendTo={o.sendTo}
          socket={socket}
          unreads={o.unreadedMessages}
          resetUnreads={() => resetUnreads(o.roomId)}
        />
      ))}
    </div>
  );
}

const mapStateToProps = (state) => ({
  openChatBoxes: state.chat.openChatBoxes,
  user: state.user.user,
});

export default connect(mapStateToProps, {
  setOpenChatBoxes,
  addOpenChatBox,
  appendMessageToRoom,
  resetUnreads,
})(OpenChatBoxes);
