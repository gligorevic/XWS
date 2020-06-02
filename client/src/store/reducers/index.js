import { combineReducers } from "redux";
import certificates from "./certificates";
import user from "./user";
import profile from "./profile";
import cars from "./cars";
import advertisement from "./advertisement";

const appReducer = combineReducers({
  certificates,
  user,
  profile,
  cars,
  advertisement,
});

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
