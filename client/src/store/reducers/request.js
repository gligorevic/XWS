import { SET_ALL_REQUESTS } from "../actionTypes";

const DEFAULT_STATE = {
  allRequests: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_ALL_REQUESTS:
      return {
        ...state,
        allRequests: action.allRequests,
      };

    default:
      return state;
  }
};
