import {
  SET_PRICELISTS,
  SET_PRICELIST_ITEMS,
  SET_ADS_FOR_PRICELIST,
} from "../actionTypes";

const DEFAULT_STATE = {
  pricelists: [],
  pricelistItems: [],
  adsForPricelist: [],
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
    default:
      return state;
  }
};
