import { SET_ALL_REQUESTS, SET_CREATED_REQUESTS } from "../actionTypes";

const DEFAULT_STATE = {
  allRequests: [],
  createdRequests: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_ALL_REQUESTS:
      return {
        ...state,
        allRequests: action.allRequests,
      };
    case SET_CREATED_REQUESTS:
      return {
        ...state,
        createdRequests: action.createdRequests,
      };

    default:
      return state;
  }
};
