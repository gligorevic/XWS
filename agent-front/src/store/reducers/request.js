import {
  SET_PASSED_REQUESTS,
  SET_RESERVED_REQUESTS,
  SET_ALL_REQUESTS,
  SET_CREATED_REQUESTS,
} from "../actionTypes";

const DEFAULT_STATE = {
  allRequests: [],
  createdRequests: [],
  passedRequests: [],
  reservedRequests: [],
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
