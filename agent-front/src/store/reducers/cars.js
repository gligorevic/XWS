import { SET_CARS, SET_TOKEN_GENERATED, SET_ADDED_CARS } from "../actionTypes";

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
    case SET_TOKEN_GENERATED: {
      return {
        ...state,
        myCars: state.myCars.map((car) =>
          car.id === action.id ? { ...car, tokenGenerated: true } : car
        ),
      };
    }
    case SET_ADDED_CARS: {
      return {
        ...state,
        myCars: [...state.myCars, action.car],
      };
    }
    default:
      return state;
  }
};
