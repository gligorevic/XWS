import { combineReducers } from "redux";
import advertisement from "./advertisement";
import user from "./user";
import cars from "./cars";
import request from "./request";
import report from "./report";
import comment from "./comment";
import statistic from "./statistic";
import pricelist from "./pricelist";

const appReducer = combineReducers({
  advertisement,
  user,
  cars,
  request,
  report,
  comment,
  statistic,
  pricelist,
});

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
