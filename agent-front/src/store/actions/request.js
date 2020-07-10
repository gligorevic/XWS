import {
  SET_PASSED_REQUESTS,
  SET_RESERVED_REQUESTS,
  SET_ALL_REQUESTS,
  SET_CREATED_REQUESTS,
} from "../actionTypes";
import axios from "axios";

export const setAllRequests = (allRequests) => ({
  type: SET_ALL_REQUESTS,
  allRequests,
});

export const setCreatedRequests = (createdRequests) => ({
  type: SET_CREATED_REQUESTS,
  createdRequests,
});

export const setPassedRequests = (passedRequests) => ({
  type: SET_PASSED_REQUESTS,
  passedRequests,
});

export const setReservedRequests = (reservedRequests) => ({
  type: SET_RESERVED_REQUESTS,
  reservedRequests,
});

export const getAllRequests = (username) => async (dispatch) => {
  try {
    const allRequests = await axios.get(`/request/info`);
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

export const payRequest = (roomId) => async (dispatch) => {
  try {
    if (roomId.startsWith("b")) {
      const res = await axios.put(`/request/bundle/${roomId.slice(1)}/pay`, {});
      console.log(res);
    } else {
      const res = await axios.put(`/request/${roomId}/pay`, {});
      console.log(res);
    }
  } catch (error) {
    console.log(error);
  }
};
