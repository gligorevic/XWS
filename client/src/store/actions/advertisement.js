import {
  SET_USER_ADVERTISEMENTS,
  SET_RESERVATION_PERIODS,
  SET_ADVERTISEMENTS,
  SET_ADVERTISEMENTS_CART,
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

export const setAllAdvertisementsForCart = (allAdvertisementsCart) => ({
  type: SET_ADVERTISEMENTS_CART,
  allAdvertisementsCart,
});

export const getUserAdvertisements = () => async (dispatch, getState) => {
  try {
    const profileState = getState().profile.profile;
    if (profileState === null) {
      const email = getState().user.user.username;
      const advertisements = await axios.get(`/search/user/${email}`);
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
    const allAdvertisements = await axios.get(`/search`);
    dispatch(setAllAdvertisements(allAdvertisements.data));
    return allAdvertisements;
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};

export const searchAdvertisements = (searchParams) => async (
  dispatch,
  getState
) => {
  try {
    const allAdvertisements = await axios.post(`/search/search`, searchParams);
    dispatch(setAllAdvertisements(allAdvertisements.data));
    return allAdvertisements;
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};

export const getAdvertisementsForCart = (idList) => async (dispatch) => {
  try {
    const allAdvertisementsCart = await axios.post(`/search/ad`, idList);
    dispatch(setAllAdvertisementsForCart(allAdvertisementsCart.data));
    return allAdvertisementsCart;
  } catch (err) {
    console.log(err.response);
    return err.response;
  }
};
