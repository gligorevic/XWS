import { SET_CARS } from "../actionTypes";

const DEFAULT_STATE = {
  myCars: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_CARS: {
      return {
        ...state,
        myCars: action.myCars,
      };
    }
    default:
      return state;
  }
};
