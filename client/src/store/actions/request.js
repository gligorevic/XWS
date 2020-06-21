import {
  SET_ALL_REQUESTS,
  SET_CREATED_REQUESTS,
  SET_ALL_PAID_REQUESTS,
} from "../actionTypes";

import axios from "axios";

export const setAllRequests = (allRequests) => ({
  type: SET_ALL_REQUESTS,
  allRequests,
});


export const setAllPaidRequests = (allPaidRequests) => ({
  type: SET_ALL_PAID_REQUESTS,
  allPaidRequests,
});


export const setCreatedRequests = (createdRequests) => ({
  type: SET_CREATED_REQUESTS,
  createdRequests,
});

export const getAllRequests = (username) => async (dispatch) => {
  try {
    const allRequests = await axios.get(`/request/info/${username}`);
    dispatch(setAllRequests(allRequests.data));
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};

export const getCreatedRequests = (username) => async (dispatch) => {
  try {
    const res = await axios.get(`/request/user/${username}/created`);
    const sortedRequestsByContainerId = res.data.sort(function (a, b) {
      return a.containerId - b.containerId;
    });
    console.log(sortedRequestsByContainerId);
    dispatch(setCreatedRequests(sortedRequestsByContainerId));
  } catch (err) {
    console.log(err);
  }
};


export const getAllPaid = (username) => async (dispatch) => {
  try {
    var allRequests = await axios.get(`/request/user/${username}/paid`);
    dispatch(setAllPaidRequests(allRequests.data));
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};

