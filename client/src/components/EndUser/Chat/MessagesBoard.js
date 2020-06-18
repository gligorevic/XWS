import React from "react";
import SockJsClient from "react-stomp";

export default function MessagesBoard() {
  let clientRef = null;

  return (
    <div>
      <SockJsClient
        url="/chat/gs-guide-websocket/"
        topics={["/message/1"]}
        onConnect={() => {
          console.log("connected");
        }}
        onDisconnect={() => {
          console.log("Disconnected");
        }}
        onMessage={(msg) => {
          console.log(msg);
        }}
        ref={(client) => {
          clientRef = client;
        }}
      />
      <p>sockjs</p>
    </div>
  );
}
