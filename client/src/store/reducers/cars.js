import { SET_CARS, SET_CAR_FOR_ADVERTISEMENT } from "../actionTypes";

const DEFAULT_STATE = {
  myCars: [],
  carForAdvertisement: null,
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_CARS: {
      return {
        ...state,
        myCars: action.myCars,
      };
    }
    case SET_CAR_FOR_ADVERTISEMENT:
      return {
        ...state,
        carForAdvertisement: action.carForAdvertisement,
      };
    default:
      return state;
  }
};
