import { SET_ALL_USERS, SET_USER_BLOCKED } from "../actionTypes";
import axios from "axios";

export const setAllUsers = (allUsers) => ({
  type: SET_ALL_USERS,
  allUsers,
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
    await axios.put(`/auth/user/toggleBlock/${userId}`);
    dispatch({ type: SET_USER_BLOCKED, id: userId });
  } catch (err) {
    console.log(err);
    return err.response;
  }
};
