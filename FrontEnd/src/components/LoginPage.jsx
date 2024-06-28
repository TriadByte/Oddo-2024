import formImage from "../assets/file.png"
import "../css/colors.css"


export default function LoginPage() {
  return(
    <>
    <div className="container">

        <div className="Container d-flex p-5 bg-third text-white rounded-5">
            <div className="left w-50 d-flex justify-content-center align-items-center">
                <div className="img">
                    <img src={formImage}  alt="" srcSet="" />
                </div>
            </div>
            <div className="right w-50">
              <div className="formbox h-100 d-flex flex-column justify-content-center align-items-center text-center">
                <div className="title">
                  <p className="fs-1">Sign In </p>
                </div>
                <div className="form  p-4">
                  <form action="#" method="post">
                  <input type="text" name="uid" id="uid" placeholder="Username / Email / Phone Number" required className="w-100 mb-4 p-3 fs-5 rounded-pill text-dark"/>
                  <input type="password" name="password" id="password" placeholder="Password" required className="w-100 p-3 fs-5 rounded-pill text-dark"/>
                  <div className="pt-2 pe-3 pb-4 float-end">
                  <a href="">Forgot password?</a>
                  </div>
                  
                  <input type="submit" className="btn bg-five w-100 text-white p-3 fs-5 rounded-pill" value="Sign In" />
                  </form>
                </div>
                <hr />
                <div className="otherMethod">
                  <div className="text-center text-white">
                    <p className="fs-5 mt-4">Other ways to sign-in</p>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <div className="text-center">
          <p className="fs-5 pt-5">
            Don't Have Account? <a href="#"><u className="fw-bold">Create New Account</u></a>
          </p>
        </div>
    </div>
    </>
  );
  
}
