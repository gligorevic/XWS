import { SET_PASSED_REQUESTS } from "../actionTypes";
import axios from "axios";

export const setPassedRequests = (passedRequests) => ({
  type: SET_PASSED_REQUESTS,
  passedRequests,
});

export const getPassedRequests = () => async (dispatch, getState) => {
  try {
    const passedRequests = await axios.get(`/request/passed`);
    dispatch(setPassedRequests(passedRequests.data));
  } catch (err) {
    console.log(err);
  }
};
