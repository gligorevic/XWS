import {
  SET_CURRENT_USER,
  SET_ALL_USERS,
  SET_USER_BLOCKED,
} from "../actionTypes";

const DEFAULT_STATE = {
  isAuthenticated: false,
  user: {},
  allUsers: [],
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        isAuthenticated: !!Object.keys(action.user).length,
        user: action.user,
      };
    case SET_ALL_USERS:
      return {
        ...state,
        allUsers: action.allUsers,
      };
    case SET_USER_BLOCKED:
      return {
        ...state,
        allUsers: state.allUsers.map((user) =>
          user.id === action.id ? { ...user, blocked: !user.blocked } : user
        ),
      };
    default:
      return state;
  }
};
