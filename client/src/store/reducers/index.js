import { combineReducers } from "redux";
import certificates from "./certificates";
import user from "./user";
import profile from "./profile";
import carInfo from "./carInfo"

const appReducer = combineReducers({ certificates, user, profile, carInfo });

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
