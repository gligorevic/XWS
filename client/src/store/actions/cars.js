import {
  SET_CARS,
  SET_CAR_FOR_ADVERTISEMENT,
  SET_TOKEN_GENERATED,
} from "../actionTypes";
import axios from "axios";

export const setCars = (myCars) => ({
  type: SET_CARS,
  myCars,
});

export const getCars = () => async (dispatch, getState) => {
  try {
    const profileState = getState().profile.profile;
    if (profileState === null) {
      const email = getState().user.user.username;
      const cars = await axios.get(`/car/owner/${email}`);
      dispatch(setCars(cars.data));
    }
  } catch (err) {
    console.log(err);
  }
};

export const setTokenGenerated = (id) => async (dispatch) => {
  try {
    dispatch({ type: SET_TOKEN_GENERATED, id });
  } catch (err) {
    console.log(err);
  }
};
