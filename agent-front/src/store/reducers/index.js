import { combineReducers } from "redux";
import advertisement from "./advertisement";
import user from "./user";
import cars from "./cars";
import request from "./request";
import report from "./report";

const appReducer = combineReducers({
  advertisement,
  user,
  cars,
  request,
  report,
});

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
