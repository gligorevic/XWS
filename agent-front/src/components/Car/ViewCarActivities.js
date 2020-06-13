import React, { useState } from "react";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import { connect } from "react-redux";
import { setTokenGenerated } from "../../store/actions/cars";
import CopyTokenToClipBoard from "./CopyTokenToClipBoard";
import axios from "axios";
import IconButton from "@material-ui/core/IconButton";
import AddLocationIcon from "@material-ui/icons/AddLocation";
import LocationOnIcon from "@material-ui/icons/LocationOn";

function ViewCarActivities({ setTokenGenerated, car }) {
  const [token, setToken] = useState(null);

  const generateToken = async (carId) => {
    try {
      const res = await axios.post("/car/locationToken", carId, {
        headers: { "Content-Type": "application/json" },
      });
      setTokenGenerated(carId);
      setToken(res.data);
      car.tokenGenerated = true;
    } catch (e) {
      console.log(e);
    }
  };

  const getExistingToken = async (carId) => {
    try {
      const res = await axios.get(`/car/locationToken/${carId}`);
      setToken(res.data);
    } catch (e) {
      console.log(e.response);
    }
  };

  return (
    <Paper elevation={3} style={{ padding: 50 }}>
      <Typography
        variant="h4"
        component="h2"
        style={{ marginBottom: 20, marginLeft: 13 }}
      >
        Activities
      </Typography>
      {car &&
        (car.tokenGenerated ? (
          <>
            <IconButton onClick={() => getExistingToken(car.id)}>
              <LocationOnIcon fontSize="large" />
            </IconButton>
            <span style={{ color: "#a1a1a1" }}>Get location token</span>
          </>
        ) : (
          <>
            <IconButton
              style={{ color: "#4caf50" }}
              onClick={() => generateToken(car.id)}
            >
              <AddLocationIcon fontSize="large" />
            </IconButton>
            <span style={{ color: "#a1a1a1" }}>Generate location token</span>
          </>
        ))}
      <CopyTokenToClipBoard
        setToken={setToken}
        open={token !== null}
        token={token}
      />
    </Paper>
  );
}

export default connect(null, { setTokenGenerated })(ViewCarActivities);
