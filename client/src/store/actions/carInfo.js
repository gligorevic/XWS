import { SET_ALL_BRANDS, SET_ALL_MODELS } from "../actionTypes";
import axios from "axios";

export const setAllBrands = (allBrands) => ({
  type: SET_ALL_BRANDS,
  allBrands,
}); 

export const setAllModels = (allModels) => ({
  type: SET_ALL_MODELS,
  allModels,
}); 

export const getAllBrands = () => async (dispatch) => {
    try {
      const res = await axios.get("/car-info/brand");
      dispatch(setAllBrands(res.data));
    } catch (err) {
      console.log(err);
      return err.response;
    }
  };
