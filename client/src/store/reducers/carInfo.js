import { SET_ALL_BRANDS, SET_ALL_MODELS } from "../actionTypes";

const DEFAULT_STATE = {
  allBrands: [],
  allModels: []
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_ALL_BRANDS:
      return {
        ...state,
        allBrands: action.allBrands,
      };
      case SET_ALL_MODELS:
      return {
        ...state,
        allModels: action.allModels,
      };
    default:
      return state;
  }
};
