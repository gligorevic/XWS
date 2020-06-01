import {
  SET_ALL_USERS,
  SET_USER_BLOCKED,
  SET_ALLUSER_DELETE,
} from "../actionTypes";
import axios from "axios";

export const setAllUsers = (allUsers) => ({
  type: SET_ALL_USERS,
  allUsers,
});

export const setAllUsersAfterDelete = (userId) => ({
  type: SET_ALLUSER_DELETE,
  userId,
});

export const getAllUsers = () => async (dispatch) => {
  try {
    const res = await axios.get("/auth/user");
    dispatch(setAllUsers(res.data));
  } catch (err) {
    console.log(err);
    return err.response;
  }
};

export const toggleBlock = (userId) => async (dispatch) => {
  try {
    await axios.put(`/auth/user/${userId}/block`);
    dispatch({ type: SET_USER_BLOCKED, id: userId });
  } catch (err) {
    console.log(err);
    return err.response;
  }
};

export const deleteUser = (userId) => async (dispatch) => {
  try {
    await axios.delete(`/auth/user/${userId}`);
    dispatch(setAllUsersAfterDelete(userId));
  } catch (err) {
    console.log(err);
    return err.response;
  }
};
