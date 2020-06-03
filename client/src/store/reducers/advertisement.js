import {
  SET_USER_ADVERTISEMENTS,
  SET_RESERVATION_PERIODS,
} from "../actionTypes";

const DEFAULT_STATE = {
  myAdvertisements: [],
  reservationPeriods: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_USER_ADVERTISEMENTS: {
      return {
        ...state,
        myAdvertisements: action.myAdvertisements,
      };
    }
    case SET_RESERVATION_PERIODS: {
      return {
        ...state,
        reservationPeriods: action.reservationPeriods,
      };
    }
    default:
      return state;
  }
};
