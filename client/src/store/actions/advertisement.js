import {
  SET_USER_ADVERTISEMENTS,
  SET_RESERVATION_PERIODS,
  SET_ADVERTISEMENTS,
} from "../actionTypes";
import axios from "axios";

export const setUserAdvertisements = (myAdvertisements) => ({
  type: SET_USER_ADVERTISEMENTS,
  myAdvertisements,
});

export const setReservationPeriods = (reservationPeriods) => ({
  type: SET_RESERVATION_PERIODS,
  reservationPeriods,
});

export const setAllAdvertisements = (allAdvertisements) => ({
  type: SET_ADVERTISEMENTS,
  allAdvertisements,
});

export const getUserAdvertisements = () => async (dispatch, getState) => {
  try {
    const profileState = getState().profile.profile;
    if (profileState === null) {
      const email = getState().user.user.username;
      const advertisements = await axios.get(`/search/${email}`);
      dispatch(setUserAdvertisements(advertisements.data));
    }
  } catch (err) {
    console.log(err);
  }
};

export const getReservationPeriods = (id) => async (dispatch, getState) => {
  try {
    const reservationPeriods = await axios.get(
      `/search/reservationPeriod/${id}`
    );
    dispatch(setReservationPeriods(reservationPeriods.data));
  } catch (err) {
    console.log(err);
  }
};

export const getAllAdvertisements = () => async (dispatch, getState) => {
  try {
    console.log("pozivam");
    const allAdvertisements = await axios.get(`/search`);
    dispatch(setAllAdvertisements(allAdvertisements.data));
    return allAdvertisements;
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};
