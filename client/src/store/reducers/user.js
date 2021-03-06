import {
  SET_CURRENT_USER,
  SET_ALL_USERS,
  SET_USER_BLOCKED,
  SET_ALLUSER_DELETE,
  SET_CART_ITEMS_NUM,
  SET_ALL_COMPANY_REQUESTS,
} from "../actionTypes";

const DEFAULT_STATE = {
  isAuthenticated: false,
  cartItemsNum: 0,
  user: {},
  allUsers: [],
  companyRequests: [],
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
    case SET_ALLUSER_DELETE:
      return {
        ...state,
        allUsers: state.allUsers.filter((user) => user.id !== action.userId),
      };
    case SET_CART_ITEMS_NUM:
      return {
        ...state,
        cartItemsNum: action.cartItemsNum,
      };
    case SET_ALL_COMPANY_REQUESTS:
      return {
        ...state,
        companyRequests: action.companyRequests,
      };
    default:
      return state;
  }
};
