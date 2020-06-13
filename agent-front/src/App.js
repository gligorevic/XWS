import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import { configureStore } from "./store/index";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./components/Home/Home";

const store = configureStore();

function App() {
  return (
    <Provider store={store}>
      <Router className="App">
        <Switch>
          <Route path="/*" component={Home} />
        </Switch>
      </Router>
    </Provider>
  );
}

export default App;
