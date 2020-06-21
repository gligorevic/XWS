import { SET_ALL_COMMENT } from "../actionTypes";
import axios from "axios";

export const setAllComments = (allComments) => ({
  type: SET_ALL_COMMENT,
  allComments,
});

export const getAllComments = () => async (dispatch) => {
  try {
    const res = await axios.get("/feedback/comment");
    dispatch(setAllComments(res.data));
  } catch (err) {
    console.log(err);
    return err.response;
  }
};
