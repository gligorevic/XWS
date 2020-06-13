import { combineReducers } from "redux";
import advertisement from "./advertisement";
import user from "./user";

const appReducer = combineReducers({ advertisement, user });

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
