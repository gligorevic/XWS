import { SET_PASSED_REQUESTS } from "../actionTypes";

const DEFAULT_STATE = {
  passedRequests: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_PASSED_REQUESTS: {
      return {
        ...state,
        passedRequests: action.passedRequests,
      };
    }
    default:
      return state;
  }
};
