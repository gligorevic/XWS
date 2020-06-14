import {
  SET_MY_ADVERTISEMENTS,
  SET_RESERVATION_PERIODS,
  SET_ADVERTISEMENTS,
  SET_ADVERTISEMENTS_CART,
} from "../actionTypes";

const DEFAULT_STATE = {
  myAdvertisements: [],
  reservationPeriods: [],
  allAdvertisements: [],
  allAdvertisementsCart: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_MY_ADVERTISEMENTS: {
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
    case SET_ADVERTISEMENTS_CART: {
      return {
        ...state,
        allAdvertisementsCart: action.allAdvertisementsCart,
      };
    }
    default:
      return state;
  }
};
