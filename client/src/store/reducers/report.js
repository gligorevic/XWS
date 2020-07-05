import { SET_REPORT } from "../actionTypes";

const DEFAULT_STATE = {
  report: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_REPORT: {
      return {
        ...state,
        report: action.report,
      };
    }
    default:
      return state;
  }
};
