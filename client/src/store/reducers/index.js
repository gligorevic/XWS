import { combineReducers } from "redux";
import certificates from "./certificates";
import user from "./user";
import profile from "./profile";
import cars from "./cars";
import advertisement from "./advertisement";
import carInfo from "./carInfo";
import request from "./request";
import chat from "./chat";
import comment from "./comment";
import report from "./report";
import pricelist from "./pricelist";
import statistic from "./statistic";

const appReducer = combineReducers({
  certificates,
  user,
  profile,
  cars,
  advertisement,
  carInfo,
  request,
  chat,
  comment,
  report,
  pricelist,
  statistic,
});

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
