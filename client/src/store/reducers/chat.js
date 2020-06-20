import { SET_OPEN_CHATBOXES } from "../actionTypes";

const DEFAULT_STATE = {
  openChatBoxes: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_OPEN_CHATBOXES: {
      return {
        ...state,
        openChatBoxes: action.openChatBoxes,
      };
    }

    default:
      return state;
  }
};
