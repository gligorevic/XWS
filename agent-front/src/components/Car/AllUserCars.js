import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { getCars, setTokenGenerated } from "../../store/actions/cars";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import IconButton from "@material-ui/core/IconButton";
import AddIcon from "@material-ui/icons/Add";
import Tooltip from "@material-ui/core/ToolTip";
import "./AllUserCars.css";
import axios from "axios";
import CopyTokenToClipBoard from "./CopyTokenToClipBoard";
import CarCard from "./CarCard";

const AllUserCars = ({
  getCars,
  cars,
  setAddCarDialogOpened,
  setTokenGenerated,
}) => {
  const [open, setOpened] = useState(false);
  const [token, setToken] = useState(null);

  useEffect(() => {
    getCars();
  }, []);

  const generateToken = async (carId) => {
    try {
      const res = await axios.post("/car/locationToken", carId, {
        headers: { "Content-Type": "application/json" },
      });
      setTokenGenerated(carId);
      setToken(res.data);
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
    <div style={{ overflow: "hidden", minHeight: "100vh" }}>
      <Typography variant="h5">My cars</Typography>
      <Divider style={{ marginBottom: 40 }} />
      <Grid container spacing={3}>
        {cars.length > 0 &&
          cars.map((car) => (
            <Grid item xs={12} sm={6} md={3} key={car.id}>
              <CarCard
                getExistingToken={getExistingToken}
                generateToken={generateToken}
                car={car}
              />
            </Grid>
          ))}
        <Grid item xs={12} sm={6} md={3}>
          <Card
            className={open ? "scaleOnClick" : ""}
            style={{
              opacity: 0.7,
              backgroundColor: "#f5f5f5",
              boxShadow: "0px 2px 12px 0px #4caf5070",
            }}
          >
            <CardContent>
              <Tooltip style={{ color: "#4caf50" }} title="Add new car">
                <IconButton
                  onClick={() => {
                    setOpened(true);
                    setTimeout(function () {
                      setAddCarDialogOpened(true);
                      setTimeout(() => setOpened(false), 140);
                    }, 400);
                  }}
                >
                  <AddIcon fontSize="large" />
                </IconButton>
              </Tooltip>
            </CardContent>
            <CardMedia className="media"></CardMedia>
          </Card>
        </Grid>
      </Grid>
      <CopyTokenToClipBoard
        setToken={setToken}
        open={token !== null}
        token={token}
      />
    </div>
  );
};

const mapStateToProps = (state) => ({
  cars: state.cars.myCars,
});

export default connect(mapStateToProps, { getCars, setTokenGenerated })(
  AllUserCars
);
