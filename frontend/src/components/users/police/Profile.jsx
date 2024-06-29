import React, { useState, useEffect } from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import '../../assets/css/Police.css'; // Import your CSS file
import axios from 'axios';


const Profile = () => {
  // Sample data for demonstration (replace with actual data handling logic)
  const [profileData, setProfileData] = useState({
    username: '',
    phone: '',
    profilePic: 'https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg',
    address: '',
    bloodGroup: '',
    dateOfBirth: '',
    city: '',
    pincode: '',
    state: '',
    nationality: '',
  });

  const [newProfileData, setNewProfileData] = useState({});


  useEffect(() => {
    axios.get("https://mocki.io/v1/d0876ba6-b042-4978-b096-9b14e418008e").then((res => {
     
      console.log(res.data);  
    setProfileData(res.data);
    setNewProfileData(res.data);

    console.log(profileData);
    }

    ))
  }, [])

  // State to manage validation errors
  const [errors, setErrors] = useState({});

  // Handle log out action
  const handleLogout = () => {
    // Implement logout logic here (e.g., clear session, redirect to login page)
    window.location.href = '/police/signin';
  };

  // Handle navigation to another page (Admin Panel)
  const handleNavigateAdmin = () => {
    window.location.href = '/police/dashboard';
  };

  // Handle input change for editable fields with validation
  const handleInputChange = (e) => {
    const { name, value } = e.target;

    setProfileData({ ...profileData, [name]: value });
    // Clear previous error message
    setErrors({ ...errors, [name]: '' });
  };



  // Validate all fields before saving changes
  const validateFields = () => {
    const newErrors = {};
    Object.keys(profileData).forEach((key) => {
      if (profileData[key].trim() === '') {
        newErrors[key] = 'This field is required';
      }
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0; // Return true if no errors
  };

  // Handle save changes action
  const handleSaveChanges = () => {
    const isValid = validateFields();
    if (isValid) {
      // Log profile data to console
      console.log('Profile Data:', profileData);
      // Implement save changes logic here (e.g., API call to save profile data)
      console.log('Changes saved');
    } else {
      console.log('Please fill in all required fields correctly');
    }
  };

  const handleDiscardChanges = () => {
    // Reset newProfileData to current profileData
    setNewProfileData(profileData);
    // Clear all errors
    setErrors({});
  }

  return (
    <section className="vh-100" style={{ backgroundColor: '#f4f5f7' }}>
      <Navbar bg="light" expand="lg" className="mb-4">
        <Container>
          <Navbar.Brand>Profile</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav" className="justify-content-end">
            <Nav>
              <Button variant="primary" className="me-2" onClick={handleNavigateAdmin}>Admin Panel</Button>
              <Button variant="danger" onClick={handleLogout}>Log Out</Button>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <div className="container rounded bg-white mt-5 mb-5 profile-container">
        <div className="row">
          <div className="col-md-3 p-2">
            {/* Profile picture */}
            <div className="d-flex flex-column align-items-center text-center p-3 py-5">
              <img
                className="rounded-circle mt-5 profile-image"
                src={profileData.profilePic}
                alt="Profile Avatar"
              />
            </div>
          </div>
          <div className="col-md-9">
            <div className="p-3 py-5">
              <div className="d-flex justify-content-between align-items-center mb-3">
                <h4 className="text-right profile-avatar">Profile Settings</h4>
              </div>
              {/* Profile information */}
              <div className="row mt-4">
                <div className="col-md-6 mb-3">
                  <label className="labels">Username</label>
                  <input
                    type="text"
                    className={`form-control ${errors.username ? 'is-invalid' : ''}`}
                    name="username"
                    value={newProfileData.username}
                    onChange={handleInputChange}
                  />
                  {errors.username && <div className="invalid-feedback">{errors.username}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">Phone</label>
                  <input
                    maxLength={10}
                    type="text"
                    className={`form-control ${errors.phone ? 'is-invalid' : ''}`}
                    name="phone"
                    value={profileData.phone}
                    onChange={handleInputChange}
                  />
                  {errors.phone && <div className="invalid-feedback">{errors.phone}</div>}
                </div>
                <div className="col-md-12 mb-3">
                  <label className="labels">Address</label>
                  <input
                    type="text"
                    className={`form-control ${errors.address ? 'is-invalid' : ''}`}
                    name="address"
                    value={profileData.address}
                    onChange={handleInputChange}
                  />
                  {errors.address && <div className="invalid-feedback">{errors.address}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">Blood Group</label>
                  <input
                    type="text"
                    className={`form-control ${errors.bloodGroup ? 'is-invalid' : ''}`}
                    name="bloodGroup"
                    value={profileData.bloodGroup}
                    onChange={handleInputChange}
                  />
                  {errors.bloodGroup && <div className="invalid-feedback">{errors.bloodGroup}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">Date of Birth</label>
                  <input
                    type='date'
                    className={`form-control ${errors.dateOfBirth ? 'is-invalid' : ''}`}
                    name="dateOfBirth"
                    value={profileData.dateOfBirth}
                    onChange={handleInputChange}
                  />
                  {errors.dateOfBirth && <div className="invalid-feedback">{errors.dateOfBirth}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">City</label>
                  <input
                    type="text"
                    className={`form-control ${errors.city ? 'is-invalid' : ''}`}
                    name="city"
                    value={profileData.city}
                    onChange={handleInputChange}
                  />
                  {errors.city && <div className="invalid-feedback">{errors.city}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">Pincode</label>
                  <input
                  maxLength={6}
                    type="text"
                    className={`form-control ${errors.pincode ? 'is-invalid' : ''}`}
                    name="pincode"
                    value={profileData.pincode}
                    onChange={handleInputChange}
                  />
                  {errors.pincode && <div className="invalid-feedback">{errors.pincode}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">State</label>
                  <input
                    type="text"
                    className={`form-control ${errors.state ? 'is-invalid' : ''}`}
                    name="state"
                    value={profileData.state}
                    onChange={handleInputChange}
                  />
                  {errors.state && <div className="invalid-feedback">{errors.state}</div>}
                </div>
                <div className="col-md-6 mb-3">
                  <label className="labels">Nationality</label>
                  <input
                    type="text"
                    className={`form-control ${errors.nationality ? 'is-invalid' : ''}`}
                    name="nationality"
                    value={profileData.nationality}
                    onChange={handleInputChange}
                  />
                  {errors.nationality && <div className="invalid-feedback">{errors.nationality}</div>}
                </div>
              </div>
              <div className="mt-5 center-button">
                <Button variant="success" onClick={handleSaveChanges}>Save Changes</Button>
                <Button variant="danger" onClick={handleDiscardChanges}>Discard Changes</Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Profile;
