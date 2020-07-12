import React, { useEffect, useState } from "react";
import { withRouter } from "react-router";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Divider from "@material-ui/core/Divider";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Gallery from "./Gallery";
import ViewCarActivities from "./ViewCarActivities";
import ViewCarInfo from "./ViewCarInfo";

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  container: {
    padding: "10px 250px",
  },
}));

function ViewCar({ match, history }) {
  const classes = useStyles();
  const [car, setCar] = useState();
  const getDateCreated = () => {
    const date = new Date(car.creationDate);
    return `${
      date.getDay() + 1
    }/${date.getMonth()}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
  };

  useEffect(() => {
    (async () => {
      try {
        const res = await Axios.get(`/car/${match.params.carId}`);
        console.log(res);
        setCar(res.data);
      } catch (e) {
        console.log(e);
      }
    })();
  }, []);

  return (
    <div>
      <AppBar position="static" color="default">
        <Toolbar>
          <span className={classes.grow}>
            <Button
              variant="contained"
              color="primary"
              onClick={() =>
                history.push({ pathname: `/user`, state: { tab: 3 } })
              }
            >
              Go Back
            </Button>
          </span>
        </Toolbar>
      </AppBar>
      <div className={classes.container}>
        <Grid container justify="space-between">
          <Grid className={classes.grow} item>
            <h2>{car && car.brandName + " " + car.modelName}</h2>
          </Grid>
          <Grid item>
            <p style={{ color: "#a1a1a1" }}>
              Created at: {car && car.creationDate && getDateCreated()}
            </p>
          </Grid>
        </Grid>
        <Divider />
        {car && (
          <Gallery
            images={car.images.map((img) => ({
              source: "/img" + img,
            }))}
          />
        )}
        <Grid container spacing={3}>
          <Grid item sm={12} lg={6}>
            <ViewCarInfo car={car} />
          </Grid>
          <Grid item sm={12} lg={6}>
            <ViewCarActivities car={car} />
          </Grid>
        </Grid>
      </div>
    </div>
  );
}

export default withRouter(ViewCar);
