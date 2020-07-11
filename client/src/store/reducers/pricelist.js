import {
  SET_PRICELISTS,
  SET_PRICELIST_ITEMS,
  SET_ADS_FOR_PRICELIST,
  SET_ADDED_PRICES,
} from "../actionTypes";

const DEFAULT_STATE = {
  pricelists: [],
  pricelistItems: [],
  adsForPricelist: [],
  addedPrices: [],
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
    case SET_ADS_FOR_PRICELIST:
      return {
        ...state,
        adsForPricelist: action.adsForPricelist,
      };
    case SET_ADDED_PRICES:
      return {
        ...state,
        addedPrices: action.addedPrices,
      };
    default:
      return state;
  }
};
