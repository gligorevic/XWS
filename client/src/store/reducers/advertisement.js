import { SET_USER_ADVERTISEMENTS } from "../actionTypes";

const DEFAULT_STATE = {
  myAdvertisements: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_USER_ADVERTISEMENTS: {
      return {
        ...state,
        myAdvertisements: action.myAdvertisements,
      };
    }
    default:
      return state;
  }
};
