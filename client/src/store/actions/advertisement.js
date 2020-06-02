import { SET_USER_ADVERTISEMENTS } from "../actionTypes";
import axios from "axios";

export const setUserAdvertisements = (myAdvertisements) => ({
  type: SET_USER_ADVERTISEMENTS,
  myAdvertisements,
});

export const getUserAdvertisements = () => async (dispatch, getState) => {
  try {
    const profileState = getState().profile.profile;
    if (profileState === null) {
      const email = getState().user.user.username;
      const advertisements = await axios.get(`/advertisement/${email}`);
      dispatch(setUserAdvertisements(advertisements.data));
    }
  } catch (err) {
    console.log(err);
  }
};
