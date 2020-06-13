import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import { configureStore } from "./store/index";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./components/Home/Home";
import AgentProfile from "./components/Home/AgentProfile";
import ViewCar from "./components/Car/ViewCar";

const store = configureStore();

function App() {
  return (
    <Provider store={store}>
      <Router className="App">
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/agent" component={AgentProfile} />
          <Route exact path="/car/:carId" component={ViewCar} />
        </Switch>
      </Router>
    </Provider>
  );
}

export default App;
