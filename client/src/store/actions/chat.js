import { SET_OPEN_CHATBOXES } from "../actionTypes";

export const setChatBoxes = (openChatBoxes) => ({
  type: SET_OPEN_CHATBOXES,
  openChatBoxes,
});

export const setOpenChatBoxes = (chatBoxes) => (dispatch) => {
  dispatch(setChatBoxes(chatBoxes));
};

export const addOpenChatBox = (chatBox) => (dispatch, getState) => {
  const openChatBoxes = getState().chat.openChatBoxes;
  console.log(openChatBoxes);
  console.log(chatBox);
  if (
    openChatBoxes.length === 0 ||
    openChatBoxes.every((ocb) => ocb.roomId !== chatBox.roomId)
  )
    dispatch(setChatBoxes([...openChatBoxes, chatBox]));
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
