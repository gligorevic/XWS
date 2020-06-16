import { SET_ADVERTISEMENT_COMMENTS } from "../actionTypes";

const DEFAULT_STATE = {
  advertisementComments: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_ADVERTISEMENT_COMMENTS: {
      return {
        ...state,
        advertisementComments: action.advertisementComments,
      };
    }
    default:
      return state;
  }
};
