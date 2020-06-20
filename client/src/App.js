import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import Admin from "./components/Admin/Admin";
import AdminHome from "./components/Home/AdminHome";
import UserHome from "./components/Home/UserHome";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { configureStore } from "./store/index";
import Home from "./components/Home/Home";
import Login from "./components/Pages/Login";
import CartPage from "./components/EndUser/Cart/CartPage";
import PrivateAdminRoute from "./routing/PrivateAdminRoute";
import PrivateUserRoute from "./routing/PrivateUserRoute";
import { setAuthorizationToken, setUser } from "./store/actions/auth";
import jwtDecode from "jwt-decode";
import Registration from "./components/Pages/Registration";
import ViewCar from "./components/EndUser/Car/ViewCar";
import ManipulateRequests from "./components/EndUser/Request/ManipulateReguests";
import CreatedRequests from "./components/EndUser/Request/CreatedRequests";
import OpenChatBoxes from "./components/EndUser/Chat/OpenChatBoxes";

const store = configureStore();

if (localStorage.jwtToken) {
  const decodedToken = jwtDecode(localStorage.jwtToken);
  const now = new Date();
  if (Date.parse(now) / 1000 >= decodedToken.exp) {
    try {
      setAuthorizationToken(false);
      store.dispatch(setUser({}));
    } catch (err) {
      console.log(err);
    }
  } else {
    try {
      setAuthorizationToken(localStorage.jwtToken);
      store.dispatch(setUser(decodedToken));
    } catch (err) {
      store.dispatch(setUser({}));
    }
  }
}

if (localStorage.Cart && JSON.parse(localStorage.Cart).length > 0) {
  store.dispatch({
    type: "SET_CART_ITEMS_NUM",
    cartItemsNum: JSON.parse(localStorage.Cart).length,
  });
}

function App() {
  return (
    <Provider store={store}>
      <Router className="App">
        <Switch>
          <Route exact path="/chat" component={CreatedRequests} />
          <PrivateAdminRoute exact path="/admin" component={AdminHome} />
          <PrivateUserRoute exact path="/user" component={UserHome} />
          <PrivateUserRoute exact path="/cart" component={CartPage} />
          <PrivateUserRoute exact path="/car/:carId" component={ViewCar} />
          <PrivateUserRoute
            exact
            path="/request/ad/:adId"
            component={ManipulateRequests}
          />
          <PrivateAdminRoute
            exact
            path="/admin/issueCertificate"
            component={Admin}
          />
          <Route exact path="/login" render={(props) => <Login {...props} />} />
          <Route
            exact
            path="/registration"
            render={(props) => <Registration {...props} />}
          />
          <Route path="/*" component={Home} />
        </Switch>
        <OpenChatBoxes />
      </Router>
    </Provider>
  );
}

export default App;
