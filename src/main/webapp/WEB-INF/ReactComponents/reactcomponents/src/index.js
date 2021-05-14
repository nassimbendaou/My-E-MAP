import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { Route, BrowserRouter as Router } from "react-router-dom";
import "./index.css";
import App from "./App";
import App3D from "./App3D";
import "./css/sign.sass";
import Login from "./Components/Login";
import ParticlesBg from "particles-bg";
import * as serviceWorker from "./serviceWorker";

const routing = (
  <Router>
    <div>
      <ParticlesBg color="#3371c2" num={200} type="cobweb" bg={true} />
      <Route exact path="/Map" component={App} />
      <Route exact path="/Map3D" component={App3D} />
      <Route exact path="/" component={Login} />
    </div>
  </Router>
);

ReactDOM.render(routing, document.getElementById("root"));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
