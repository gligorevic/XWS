import { SET_ADVERTISEMENT_COMMENTS } from "../actionTypes";
import axios from "axios";

export const setAdvertisementComments = (advertisementComments) => ({
  type: SET_ADVERTISEMENT_COMMENTS,
  advertisementComments,
});

export const getAdvertisementComments = (id) => async (dispatch, getState) => {
  try {
    const advertisementComments = await axios.get(`/comment/${id}`);
    dispatch(setAdvertisementComments(advertisementComments.data));
  } catch (err) {
    console.log(err);
  }
};
