import { SET_ALL_COMMENT } from "../actionTypes";

const DEFAULT_STATE = {
  allComments: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_ALL_COMMENT:
      return {
        ...state,
        allComments: action.allComments,
      };
    default:
      return state;
  }
};
