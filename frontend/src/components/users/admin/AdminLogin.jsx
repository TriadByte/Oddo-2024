import React from "react";

import "../../assets/css/ClientLogin.css";

const ClientLogin = () => {
  return (
    
    <div className="container-fluid">
      <div className="row justify-content-center">
        <div className="col-md-12 col-lg-10">
          <div className="wrap d-md-flex justify-content-center">
            <div
              className="imgC"
            ></div>
            <div className="login-wrap p-5 p-md-5 bg-white">
              <div className="d-flex">
                <div className="w-100">
                  <h3 className="mb-4 mt-4 text-center fs-2">Sign In</h3>
                </div>
              </div>
              <form action="#" className="signin-form">
                <div className="form-group mb-3">
                  
                  <input
                    type="text"
                    className="form-control custom-form-control"
                    placeholder="phone number, username or email"
                    required
                    />
                </div>
                <div className="form-group mb-3">
               
                  <input
                    type="password"
                    className="form-control custom-form-control"
                    placeholder="password"
                    required
                  />
                </div>
                <div className="form-group mb-3">
                  <button
                    type="submit"
                    className="form-control btn btn-primary rounded submit px-3"
                  >
                    Sign In
                  </button>
                </div>
                <div className="form-group d-md-flex mb-3">
                  
                  <div className="w-100 text-md-right text-center">
                    <a href="#">Forgot Password ?</a>
                  </div>
                </div>
              </form>
              <p className="text-center">
                Not a member?{" "}
                <a data-toggle="tab" href="#signup">
                  Sign Up
                </a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClientLogin;
