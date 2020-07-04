import {
  SET_ALL_REQUESTS,
  SET_CREATED_REQUESTS,
  SET_ALL_PAID_REQUESTS,
  SET_NEW_REQUEST_STATUS,
  SET_NEW_BUNDLE_REQUEST_STATUS,
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

export const setNewRequestStatus = (requestId, status) => ({
  type: SET_NEW_REQUEST_STATUS,
  requestId,
  status,
});

export const setNewBundleRequestStatus = (containerId, status) => ({
  type: SET_NEW_BUNDLE_REQUEST_STATUS,
  containerId,
  status,
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

export const payRequest = (roomId) => async (dispatch) => {
  try {
    if (roomId.startsWith("b")) {
      const res = await axios.put(`/request/bundle/${roomId.slice(1)}/pay`, {});
      dispatch(setNewBundleRequestStatus(roomId.slice(1), "PAID"));
      console.log(res);
    } else {
      const res = await axios.put(`/request/${roomId}/pay`, {});
      console.log(res);
      dispatch(setNewRequestStatus(roomId, "PAID"));
    }
  } catch (error) {
    console.log(error);
  }
};

export const cancelRequest = (roomId) => async (dispatch) => {
  try {
    if (roomId.startsWith("b")) {
      const res = await axios.put(
        `/request/bundle/${roomId.slice(1)}/cancel`,
        {}
      );
      dispatch(setNewBundleRequestStatus(roomId.slice(1), "CANCELED"));
      console.log(res);
    } else {
      const res = await axios.put(`/request/${roomId}/cancel`, {});
      console.log(res);
      dispatch(setNewRequestStatus(roomId, "CANCELED"));
    }
  } catch (err) {
    console.log(err);
  }
};
