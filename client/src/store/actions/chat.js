import { SET_OPEN_CHATBOXES } from "../actionTypes";
import axios from "axios";

export const setChatBoxes = (openChatBoxes) => ({
  type: SET_OPEN_CHATBOXES,
  openChatBoxes,
});

export const setOpenChatBoxes = (chatBoxes) => (dispatch) => {
  dispatch(setChatBoxes(chatBoxes));
};

export const addOpenChatBox = (room) => async (dispatch, getState) => {
  const messagesRes = await axios.post("/chat/message/", {
    roomId: room.room,
  });
  console.log(room);
  const chatBox = {
    roomId: room.room,
    chatName: room.chatName,
    sendTo: room.name,
    unreadedMessages: 1,
    messages: messagesRes.data,
  };
  const openChatBoxes = getState().chat.openChatBoxes;
  console.log(openChatBoxes);
  console.log(chatBox);
  if (
    openChatBoxes.length === 0 ||
    openChatBoxes.every((ocb) => ocb.roomId !== chatBox.roomId)
  )
    dispatch(setChatBoxes([...openChatBoxes, chatBox]));
};

export const initializeNewChatBox = (newChatBox) => async (
  dispatch,
  getState
) => {
  const openChatBoxes = getState().chat.openChatBoxes;
  console.log(newChatBox);
  if (!openChatBoxes.find((ocb) => ocb.roomId === newChatBox.roomId)) {
    const messagesRes = await axios.post("/chat/message/", {
      roomId: newChatBox.roomId,
    });
    newChatBox.messages = messagesRes.data;
    dispatch(setChatBoxes([...openChatBoxes, newChatBox]));
  }
};

export const appendMessageToRoom = (message) => (dispatch, getState) => {
  const openChatBoxes = getState().chat.openChatBoxes;

  const settedOCBS = openChatBoxes.map((ocb) =>
    ocb.roomId === message.roomId
      ? {
          ...ocb,
          unreadedMessages: ocb.unreadedMessages + 1,
          messages: [...ocb.messages, message],
        }
      : ocb
  );

  dispatch(setChatBoxes(settedOCBS));
};

export const resetUnreads = (roomId) => (dispatch, getState) => {
  const openChatBoxes = getState().chat.openChatBoxes;

  const settedOCBS = openChatBoxes.map((ocb) =>
    ocb.roomId === roomId
      ? {
          ...ocb,
          unreadedMessages: 0,
        }
      : ocb
  );

  dispatch(setChatBoxes(settedOCBS));
};
