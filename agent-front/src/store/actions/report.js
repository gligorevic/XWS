import { SET_REPORT } from "../actionTypes";
import axios from "axios";

export const setReport = (report) => ({
  type: SET_REPORT,
  report,
});

export const getReport = (id) => async (dispatch, getState) => {
  try {
    const report = await axios.get(`/report/${id}`);
    dispatch(setReport(report.data));
  } catch (err) {
    console.log(err);
  }
};
