import {
  SET_USER_ADVERTISEMENTS,
  SET_RESERVATION_PERIODS,
  SET_ADVERTISEMENTS,
} from "../actionTypes";

const DEFAULT_STATE = {
  myAdvertisements: [],
  reservationPeriods: [],
  allAdvertisements: [],
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
    case SET_ADVERTISEMENTS: {
      return {
        ...state,
        allAdvertisements: action.allAdvertisements,
      };
    }
    default:
      return state;
  }
};
