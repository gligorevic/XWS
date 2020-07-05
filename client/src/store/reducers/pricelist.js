import { SET_PRICELISTS, SET_PRICELIST_ITEMS } from "../actionTypes";

const DEFAULT_STATE = {
  pricelists: [],
  pricelistItems: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_PRICELISTS:
      return {
        ...state,
        pricelists: action.pricelists,
      };
    case SET_PRICELIST_ITEMS:
      return {
        ...state,
        pricelistItems: action.pricelistItems,
      };
    default:
      return state;
  }
};
