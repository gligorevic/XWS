import { SET_ALL_REQUESTS } from "../actionTypes";
import axios from "axios";

export const setAllRequests = (allRequests) => ({
  type: SET_ALL_REQUESTS,
  allRequests,
});

export const getAllRequests = (username) => async (dispatch) => {
  try {
    var allRequests = await axios.get(`/request/info/${username}`);
    dispatch(setAllRequests(allRequests.data));
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};
