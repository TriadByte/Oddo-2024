import React, { useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import "leaflet/dist/leaflet.css";
import "../../assets/css/Dash.css";

const PoliceDash = () => {
  const [crimes, setCrimes] = useState([
    {
      crimeType: "Murder",
      crimeDesc: "Killed 2 Persons",
      crimeLong: 34,
      crimeLat: 76,
      anonymous: true,
      userId: 675,
      date: "2024-06-28",
      time: "14:30",
    },
    {
      crimeType: "Theft",
      crimeDesc: "50000 Rupees stolen",
      crimeLong: 34,
      crimeLat: 56,
      anonymous: true,
      userId: 432,
      date: "2024-06-29",
      time: "10:00",
    },
    {
      crimeType: "Burglary",
      crimeDesc: "House broken into",
      crimeLong: 23,
      crimeLat: 100,
      anonymous: true,
      userId: 578,
      date: "2024-06-30",
      time: "18:45",
    },
    // Add more crimes as needed
    {
      crimeType: "Assault",
      crimeDesc: "Physical altercation reported",
      crimeLong: 30,
      crimeLat: 80,
      anonymous: true,
      userId: 123,
      date: "2024-07-01",
      time: "08:15",
    },
    {
      crimeType: "Robbery",
      crimeDesc: "Robbery at a convenience store",
      crimeLong: 32,
      crimeLat: 70,
      anonymous: true,
      userId: 456,
      date: "2024-07-02",
      time: "17:20",
    },
    {
      crimeType: "Vandalism",
      crimeDesc: "Public property damaged",
      crimeLong: 28,
      crimeLat: 90,
      anonymous: true,
      userId: 789,
      date: "2024-07-03",
      time: "13:45",
    },
    {
      crimeType: "Fraud",
      crimeDesc: "Financial fraud reported",
      crimeLong: 31,
      crimeLat: 85,
      anonymous: true,
      userId: 987,
      date: "2024-07-04",
      time: "11:30",
    },
    {
      crimeType: "Drug Possession",
      crimeDesc: "Illegal substances found",
      crimeLong: 33,
      crimeLat: 65,
      anonymous: true,
      userId: 246,
      date: "2024-07-05",
      time: "09:55",
    },
    {
      crimeType: "Kidnapping",
      crimeDesc: "Child abduction reported",
      crimeLong: 29,
      crimeLat: 95,
      anonymous: true,
      userId: 135,
      date: "2024-07-06",
      time: "16:10",
    },
    {
      crimeType: "Arson",
      crimeDesc: "Deliberate fire incident",
      crimeLong: 27,
      crimeLat: 88,
      anonymous: true,
      userId: 642,
      date: "2024-07-07",
      time: "20:00",
    },
  ]);

  const [expandedCrimeIndex, setExpandedCrimeIndex] = useState(null);

  const toggleCrimeDetails = (index) => {
    setExpandedCrimeIndex(expandedCrimeIndex === index ? null : index);
  };

  const cancelCrimeDetails = () => {
    setExpandedCrimeIndex(null);
  };

  return (
    <>
      <div className="container">
        <div className="row mb-3 p-5">
          <h1>Police Dashboard</h1>
        </div>
        <div className="row">
          <div className="col-12 mb-3">
            <ul className="crime-list">
              {crimes.map((crime, index) => (
                <li
                  className="crime-item"
                  key={crime.userId}
                  onClick={() => toggleCrimeDetails(index)}
                >
                  <h3>{crime.crimeType}</h3>
                  <p>{crime.crimeDesc}</p>
                  {expandedCrimeIndex === index && (
                    <div className="expanded-crime-details">
                      <button className="cancel-btn" onClick={cancelCrimeDetails}>
                        <FontAwesomeIcon icon={faTimes} />
                      </button>
                      <h2>Crime Details</h2>
                      <p>Type: {crimes[expandedCrimeIndex].crimeType}</p>
                      <p>Description: {crimes[expandedCrimeIndex].crimeDesc}</p>
                      <p>Date: {crimes[expandedCrimeIndex].date}</p>
                      <p>Time: {crimes[expandedCrimeIndex].time}</p>
                      <MapContainer
                        center={[
                          crimes[expandedCrimeIndex].crimeLat,
                          crimes[expandedCrimeIndex].crimeLong,
                        ]}
                        zoom={13}
                        className="crime-map"
                      >
                        <TileLayer
                          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                          attribution="&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
                        />
                        <Marker
                          position={[
                            crimes[expandedCrimeIndex].crimeLat,
                            crimes[expandedCrimeIndex].crimeLong,
                          ]}
                        >
                          <Popup>
                            {crimes[expandedCrimeIndex].crimeType} at{" "}
                            {crimes[expandedCrimeIndex].crimeLat},{" "}
                            {crimes[expandedCrimeIndex].crimeLong}
                          </Popup>
                        </Marker>
                      </MapContainer>
                    </div>
                  )}
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default PoliceDash;
