import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import { configureStore } from "./store/index";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./components/Home/Home";
import AgentProfile from "./components/Home/AgentProfile";
import ViewCar from "./components/Car/ViewCar";
import AdDetails from "./components/Advertisement/AdDetails";
import Login from "./components/Pages/Login";
import { setAuthorizationToken, setUser } from "./store/actions/auth";
import jwtDecode from "jwt-decode";
import PrivateAgentRoute from "./routing/PrivateUserRoute";
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
  console.log(localStorage.Cart);
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
          <Route exact path="/" component={Home} />
          <PrivateAgentRoute exact path="/agent" component={AgentProfile} />
          <PrivateAgentRoute exact path="/car/:carId" component={ViewCar} />
          <Route exact path="/ad/:adId" component={AdDetails} />
          <Route exact path="/login" render={(props) => <Login {...props} />} />
        </Switch>
      </Router>
    </Provider>
  );
}

export default App;
