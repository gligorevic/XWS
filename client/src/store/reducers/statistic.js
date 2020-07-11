import { SET_STATISTIC } from "../actionTypes";

const DEFAULT_STATE = {
  statistic: null,
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_STATISTIC: {
      return {
        ...state,
        statistic: action.statistic,
      };
    }
    default:
      return state;
  }
};
