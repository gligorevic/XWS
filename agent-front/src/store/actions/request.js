import { SET_PASSED_REQUESTS, SET_RESERVED_REQUESTS } from "../actionTypes";
import axios from "axios";

export const setPassedRequests = (passedRequests) => ({
  type: SET_PASSED_REQUESTS,
  passedRequests,
});

export const setReservedRequests = (reservedRequests) => ({
  type: SET_RESERVED_REQUESTS,
  reservedRequests,
});

export const getPassedRequests = () => async (dispatch, getState) => {
  try {
    const passedRequests = await axios.get(`/request/passed`);
    dispatch(setPassedRequests(passedRequests.data));
  } catch (err) {
    console.log(err);
  }
};

export const getReservedRequests = () => async (dispatch, getState) => {
  try {
    const reservedRequests = await axios.get(`/request/reserved`);
    dispatch(setReservedRequests(reservedRequests.data));
  } catch (err) {
    console.log(err);
  }
};
