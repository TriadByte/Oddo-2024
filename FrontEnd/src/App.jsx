import { useState } from "react";
import "./App.css";
import "./css/colors.css";
import LoginPage from "./components/LoginPage";
import RegistraionPage from "./components/RegistrationPage";
import Navbar from "./components/Navbar";
import ClientLogin from "./User/client/ClientLogin";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      {/* <Navbar></Navbar> */}
      <div className="w-100 h-100 d-flex justify-content-center align-items-center">
        {/* <RegistraionPage></RegistraionPage> */}
        <ClientLogin></ClientLogin>
        {/* <LoginPage></LoginPage> */}
      </div>
    </>
  );
}

export default App;
