import React, { useState, useEffect } from "react";
import { connect } from "react-redux";
import io from "socket.io-client";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Divider from "@material-ui/core/Divider";
import "./Chat.css";
import CloseIcon from "@material-ui/icons/Close";
import Tooltip from "@material-ui/core/Tooltip";
import SendIcon from "@material-ui/icons/Send";
import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";

let socket = null;
function Chat({
  user,
  roomId,
  exposed,
  exposeChat,
  collapseChat,
  closeChat,
  chatName,
  sendTo,
  appendMessageToRoom,
  messages,
  socket,
  unreads,
  resetUnreads,
}) {
  const [message, setMessage] = useState("");

  const getDateCreated = (dateC) => {
    const date = new Date(dateC);
    return `${
      date.getDay() + 1
    }/${date.getMonth()}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
  };

  useEffect(() => {
    socket.emit(
      "join",
      { name: user.username, room: roomId, recieverEmail: sendTo, chatName },
      (error) => {
        if (error) {
          alert(error);
        }
      }
    );
  }, []);

  const sendMessage = (event) => {
    event.preventDefault();
    if (message) {
      socket.emit(
        "sendMessage",
        {
          text: message,
          roomId: roomId,
          recieverEmail: sendTo,
          sender: user.username,
        },
        () => setMessage("")
      );
    }
  };

  const toggleExposed = () => {
    if (exposed) {
      collapseChat();
      resetUnreads();
    } else {
      resetUnreads();
      exposeChat();
    }
  };

  const closeChatAndRemoveUserFromRoom = () => {
    console.log("emiting disconect");
    if (socket !== null) {
      socket.emit("disconnectUser", { roomId, username: user.username });
      socket = null;
    }
    closeChat();
  };

  return (
    <Card
      style={{
        width: exposed ? 260 : 200,
        height: exposed ? "100%" : "auto",
        margin: "0px 10px",
        position: "relative",
      }}
    >
      <div
        style={{
          padding: exposed ? 10 : 5,
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
        onClick={toggleExposed}
        className="chatBox"
      >
        <div>
          <span style={{ margin: "0px 4px" }}>{chatName}</span>
          {!exposed && unreads > 0 && (
            <span style={{ marginLeft: 8 }}>
              <Badge badgeContent={unreads} color="error" />
            </span>
          )}
        </div>
        <span>
          <Tooltip title="Close this chat">
            <CloseIcon onClick={closeChatAndRemoveUserFromRoom} />
          </Tooltip>
        </span>
      </div>
      <CardContent
        style={{
          display: exposed ? "block" : "none",
          padding: 0,
          top: 0,
        }}
      >
        <Divider />
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-start",
            alignItems: "center",
            height: 210,
            overflowY: "auto",
          }}
        >
          {messages.map((m, i) => (
            <div
              style={{
                margin: 4,
                alignSelf: m.user === user.username ? "flex-end" : "flex-start",
                maxWidth: "70%",
                borderRadius: 15,
                borderBottomRightRadius: m.user === user.username ? 0 : 15,
                borderBottomLeftRadius: m.user !== user.username ? 0 : 15,
                background: m.user === user.username ? "#1976d2" : "#e0e0e0",
                color: m.user === user.username ? "#ffffff" : "#000000dd",
                padding: "3px 6px",
                whiteSpace: "pre-wrap",
                wordWrap: "break-word",
              }}
              key={i}
            >
              <Tooltip title={`${m.user} at ${getDateCreated(m.date)}`}>
                <span>{m.text}</span>
              </Tooltip>
            </div>
          ))}
        </div>

        <div className="chat-input">
          <Divider />
          <div className="input-wrapper">
            <input
              type="text"
              placeholder="Type a message..."
              value={message}
              onChange={({ target: { value } }) => setMessage(value)}
              onKeyPress={(event) =>
                event.key === "Enter" ? sendMessage(event) : null
              }
            />
            <Tooltip title="Send">
              <IconButton
                color="primary"
                disabled={message.length === 0}
                onClick={(e) => sendMessage(e)}
              >
                <SendIcon />
              </IconButton>
            </Tooltip>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default connect(mapStateToProps, {})(Chat);
