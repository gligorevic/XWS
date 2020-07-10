import React, { useState, useEffect } from "react";
import { useTheme } from "@material-ui/core/styles";

import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";

import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Divider from "@material-ui/core/Divider";
import CloseIcon from "@material-ui/icons/Close";
import Tooltip from "@material-ui/core/Tooltip";
import SendIcon from "@material-ui/icons/Send";
import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";
import axios from "axios";
import "./MessageBox.css";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const MessageBox = ({ id, selectedRequestId, user }) => {
  const [messages, setMessages] = useState(null);
  const [message, setMessage] = useState([]);
  useEffect(() => {
    setInterval(async () => {
      const res = await axios.get(`/message/request/${selectedRequestId}`);
      setMessages(res.data);
    }, 1000);
  }, []);

  useEffect(() => {
    (async () => {
      const res = await axios.get(`/message/request/${selectedRequestId}`);
      setMessages(res.data);
    })();
  }, [selectedRequestId]);

  const sendMessage = async () => {
    if (message.length > 0) {
      const res = await axios.post(`/message`, {
        text: message,
        sentBy: user.id,
        requestId: selectedRequestId,
      });
      if (res.status < 300) setMessage("");
    }
  };

  return (
    <>
      {messages && (
        <Card
          style={{
            margin: "0px 10px",
            height: "100%",
          }}
        >
          <CardContent
            style={{
              padding: 0,
              position: "relative",
            }}
          >
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "flex-start",
                alignItems: "center",

                height: 400,
                overflowY: "auto",
              }}
            >
              {messages.map((m, i) => (
                <div
                  style={{
                    margin: 4,
                    alignSelf:
                      m.sentBy.email === user.username
                        ? "flex-end"
                        : "flex-start",
                    maxWidth: "70%",
                    borderRadius: 15,
                    borderBottomRightRadius:
                      m.sentBy.email === user.username ? 0 : 15,
                    borderBottomLeftRadius:
                      m.sentBy.email !== user.username ? 0 : 15,
                    background:
                      m.sentBy.email === user.username ? "#1976d2" : "#e0e0e0",
                    color:
                      m.sentBy.email === user.username
                        ? "#ffffff"
                        : "#000000dd",
                    padding: "3px 6px",
                    whiteSpace: "pre-wrap",
                    wordWrap: "break-word",
                  }}
                  key={i}
                >
                  <Tooltip title={`${m.sentBy.email} at ${m.timeSent}`}>
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
      )}
    </>
  );
};
const mapStateToProps = (state) => ({
  comments: state.comment.advertisementComments,
});

export default withRouter(connect(mapStateToProps, {})(MessageBox));
