import { SET_PRICELISTS, SET_PRICELIST_ITEMS } from "../actionTypes";
import axios from "axios";

export const setPricelists = (pricelists) => ({
  type: SET_PRICELISTS,
  pricelists,
});

export const setPricelistItems = (pricelistItems) => ({
  type: SET_PRICELIST_ITEMS,
  pricelistItems,
});

export const getPricelists = () => async (dispatch) => {
  try {
    const res = await axios.get("/price-list");
    dispatch(setPricelists(res.data));
  } catch (err) {
    console.log(err);
    return err.response;
  }
};

export const getPricelistItems = (id) => async (dispatch) => {
  try {
    const res = await axios.get(`/price-list/items/${id}`);
    dispatch(setPricelistItems(res.data));
  } catch (err) {
    console.log(err);
    return err.response;
  }
};
