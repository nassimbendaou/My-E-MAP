import React, { Component } from "react";

import ParticlesBg from "particles-bg";
import SignIn from "./SignIn";
import "./style.css";

class Login extends Component {
  constructor() {
    super();
    this.state = {
      name: "React",
    };
  }

  render() {
    return (
      <div>
        <SignIn />
      </div>
    );
  }
}
export default Login;
