import { SET_STATISTIC } from "../actionTypes";
import axios from "axios";

export const setStatistic = (statistic) => ({
  type: SET_STATISTIC,
  statistic,
});

export const getStatistic = () => async (dispatch, getState) => {
  try {
    const statistic = await axios.get(`/request/statistic`);
    dispatch(setStatistic(statistic.data));
  } catch (err) {
    console.log(err);
  }
};
