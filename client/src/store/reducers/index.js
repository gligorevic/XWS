import { combineReducers } from "redux";
import certificates from "./certificates";
import user from "./user";
import profile from "./profile";
import cars from "./cars";
import advertisement from "./advertisement";
import carInfo from "./carInfo";
import request from "./request";
import chat from "./chat";

const appReducer = combineReducers({
  certificates,
  user,
  profile,
  cars,
  advertisement,
  carInfo,
  request,
  chat,
});

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
