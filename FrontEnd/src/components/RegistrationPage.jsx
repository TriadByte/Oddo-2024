import React, { useState } from "react";
import formImage from "../assets/file.png";
import "../css/colors.css";

export default function RegistrationPage() {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    password: "",
    cPassword: "",
  });
  const [errors, setErrors] = useState({});

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  };

  const validateStep1 = () => {
    const errors = {};
    if (!formData.firstName) errors.firstName = "First Name is required";
    if (!formData.middleName) errors.middleName = "Middle Name is required";
    if (!formData.lastName) errors.lastName = "Last Name is required";
    if (!formData.email) {
      errors.email = "Email is required";
    } else if (!validateEmail(formData.email)) {
      errors.email = "Invalid email format";
    }
    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const validateStep2 = () => {
    const errors = {};
    if (!formData.phoneNumber) errors.phoneNumber = "Phone Number is required !!";
    if (!formData.password) errors.password = "Password is required !!";
    if (formData.password !== formData.cPassword)
      errors.cPassword = "Passwords do not match !!";
    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData({
      ...formData,
      [name]: files ? files[0] : value,
    });
  };

  const handleNext = () => {
    if (validateStep1()) {
      setStep(2);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateStep2()) {
      // Submit form
      console.log(JSON.stringify(formData));
    }
  };

  return (
    <div className="container">
      <div className="Container d-flex mt-5 p-5 bg-third text-white rounded-5">
        <div className="left w-50 d-flex justify-content-center align-items-center">
          <div className="img">
            <img src={formImage} alt="Form" />
          </div>
        </div>
        <div className="right w-50">
          <div className="formbox h-100 d-flex justify-content-center flex-column align-items-center text-center">
            <div className="title">
              <p className="fs-1">Create New Account</p>
            </div>
            <div className="form p-4">
              <form onSubmit={handleSubmit}>
                {step === 1 && (
                  <>
                  {errors.fname && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.fname}</p>
                    )}

                    <input
                      type="text"
                      name="firstName"
                      id="firstName"
                      required
                      placeholder="First Name"
                      className="w-100 p-3 mb-4 fs-5 text-dark rounded-pill"
                      value={formData.fname}
                      onChange={handleChange}
                    />
                     {errors.mname && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.mname}</p>
                    )}
                    <input
                      type="text"
                      name="middleName"
                      id="middleName"required
                      placeholder="Middle Name"
                      className="w-100 mb-4 p-3 fs-5 text-dark rounded-pill"
                      value={formData.mname}
                      onChange={handleChange}
                    />
                     {errors.lname && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.lname}</p>
                    )}
                    <input
                      type="text"
                      name="lastName"
                      id="lastName" required
                      placeholder="Last Name"
                      className="w-100 mb-4 p-3 fs-5 text-dark rounded-pill"
                      value={formData.lname}
                      onChange={handleChange}
                    />
                   {errors.email && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.email}</p>
                    )}
                    <input
                      type="email"
                      name="email" required
                      id="email"
                      placeholder="Email"
                      className="w-100 mb-4 p-3 fs-5 text-dark rounded-pill"
                      value={formData.email}
                      onChange={handleChange}
                    />
                   
                    <input
                      type="button" 
                      className="btn bg-five w-25 mb-4 float-end text-white p-3 fs-5 rounded-pill"
                      value="Next"
                      onClick={handleNext}
                    />
                  </>
                )}
                {step === 2 && (
                  <>
                    <input
                      type="number"
                      name="phoneNumber" required
                      id="phoneNumber"
                      placeholder="Phone Number"
                      className="w-100 mb-4 p-3 fs-5 text-dark rounded-pill"
                      value={formData.number}
                      onChange={handleChange}
                    />
                    {errors.number && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.number}</p>
                    )}
                    {/* <input
                      type="file"
                      name="pic" required
                      id="pic"
                      className="w-100 bg-light text-dark mb-4  p-3 fs-5 rounded-pill"
                      onChange={handleChange}
                    /> */}
                    <input
                      type="password"
                      name="password"
                      id="password" required
                      placeholder="Create Password"
                      className="w-100 mb-4 p-3 text-dark fs-5 rounded-pill"
                      value={formData.password}
                      onChange={handleChange}
                    />
                    {errors.password && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.password}</p>
                    )}
                    <input
                      type="password"
                      name="cPassword" required
                      id="cPassword"
                      placeholder="Confirm Password"
                      className="w-100 mb-4 p-3 text-dark fs-5 rounded-pill"
                      value={formData.c_password}
                      onChange={handleChange}
                    />
                    {errors.c_password && (
                      <p className="text-white pt-1 ps-3 pb-2 float-start">{errors.c_password}</p>
                    )}
                    <input
                      type="submit"
                      className="btn bg-five w-25 mb-4 float-end text-white p-3 fs-5 rounded-pill"
                      value="Submit"
                    />
                  </>
                )}
              </form>
            </div>
          </div>
        </div>
      </div>
      <div className="text-center">
        <p className="fs-5 pt-5">
          Already Have Account?{" "}
          <a href="#">
            <u className="fw-bold">Sign-in Here</u>
          </a>
        </p>
      </div>
    </div>
  );
}
