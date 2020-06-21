import { SET_PASSED_REQUESTS, SET_RESERVED_REQUESTS } from "../actionTypes";

const DEFAULT_STATE = {
  passedRequests: [],
  reservedRequests: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_PASSED_REQUESTS: {
      return {
        ...state,
        passedRequests: action.passedRequests,
      };
    }
    case SET_RESERVED_REQUESTS: {
      return {
        ...state,
        reservedRequests: action.reservedRequests,
      };
    }
    default:
      return state;
  }
};
