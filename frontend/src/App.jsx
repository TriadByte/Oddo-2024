import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import Profile from "./components/users/police/Profile";
import PoliceLogin from "./components/users/police/PoliceLogin";
import PoliceSignup from "./components/users/police/PoliceSignup";
import PoliceDash from "./components/users/police/PoliceDash";
import "./App.css";



function App() {
  const [showLogin, setShowLogin] = useState(true);

  return (
    <>
      <Routes>
        <Route path="/" element={<>Hello</>} />

        <Route path="*" element={<>404</>} />

        {/* Police Routes */}
        <Route path="/police/signin" element={<PoliceLogin/>} />
        <Route path="/police/profile" element={<Profile/>} />
        <Route path="/police/signup" element={<PoliceSignup />} />
        <Route path="/police/dashboard" element={<PoliceDash />} />

        {/* Admin Routes */}

        <Route path="/admin/signin" element={<>Admin Login</>} />
        <Route path="/admin/signup" element={<>Admin Signup</>} />
        <Route path="/admin/profile" element={<>Admin Profile</>} />
        <Route path="/admin/dashboard" element={<>Admin Dashboard</>} />

        {/* Client Routes */}

      </Routes>
    </>
  );
}

export default App;
