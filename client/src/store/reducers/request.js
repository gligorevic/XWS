import {
  SET_ALL_REQUESTS,
  SET_CREATED_REQUESTS,
  SET_ALL_PAID_REQUESTS,
  SET_NEW_BUNDLE_REQUEST_STATUS,
  SET_NEW_REQUEST_STATUS,
  SET_PASSED_REQUESTS,
} from "../actionTypes";


const DEFAULT_STATE = {
  allRequests: [],
  createdRequests: [],
  allPaidRequests: [],
  passedRequests: [],
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
    case SET_ALL_PAID_REQUESTS:
      return {
        ...state,
        allPaidRequests: action.allPaidRequests,
      };
    case SET_PASSED_REQUESTS:
      return {
        ...state,
        passedRequests: action.passedRequests,
      };
    case SET_NEW_REQUEST_STATUS:
      return {
        ...state,
        createdRequests: state.createdRequests.map((r) =>
          r.id == action.requestId ? { ...r, paidState: action.status } : r
        ),
      };
    case SET_NEW_BUNDLE_REQUEST_STATUS:
      return {
        ...state,
        createdRequests: state.createdRequests.map((r) =>
          r.containerId == action.containerId
            ? { ...r, paidState: action.status }
            : r
        ),
      };
    default:
      return state;
  }
};
