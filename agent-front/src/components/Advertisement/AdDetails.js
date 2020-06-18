import React, { useEffect, useState } from "react";
import { withRouter } from "react-router";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Divider from "@material-ui/core/Divider";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/Grid";
import Gallery from "../Car/Gallery";

import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Typography from "@material-ui/core/Typography";
import { Paper } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  container: {
    padding: "10px 250px",
  },
}));

function AdDetails({ match }) {
  const classes = useStyles();
  const [ad, setAd] = useState();

  useEffect(() => {
    (async () => {
      try {
        const res = await Axios.get(`/search/${match.params.adId}`);
        console.log(res);
        setAd(res.data);
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
              color="secondary"
              onClick={() => window.history.back()}
            >
              Go Back
            </Button>
          </span>
        </Toolbar>
      </AppBar>
      <div className={classes.container}>
        <Grid container justify="space-between">
          <Grid className={classes.grow} item>
            <h2>{ad && ad.carDTO.brandName + " " + ad.carDTO.modelName}</h2>
          </Grid>
          <Grid item>
            <p style={{ color: "#a1a1a1" }}>Free from: {ad && ad.freeFrom}</p>
          </Grid>
        </Grid>
        <Divider />
        {ad && (
          <Gallery
            images={ad.carDTO.images.map((img) => ({
              source: img,
            }))}
          />
        )}
        <Grid container spacing={3}>
          <Grid item sm={12} lg={6}>
            <Paper style={{ padding: 50 }}>
              <Typography
                variant="h4"
                component="h2"
                style={{ marginBottom: 20, marginLeft: 13 }}
              >
                Car
              </Typography>
              {ad && (
                <>
                  <List disablePadding>
                    <ListItem>
                      <ListItemText primary="Brand" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.brandName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Model" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.modelName}
                      </Typography>
                    </ListItem>
                    <Divider />
                    <ListItem>
                      <ListItemText primary="Gear shift" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.gearShiftName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Fuel type" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.fuelTypeName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Body" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.bodyName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Km passed" />
                      <Typography variant="subtitle1">
                        {ad.carDTO.kmPassed}
                      </Typography>
                    </ListItem>

                    <Divider />
                  </List>
                </>
              )}
            </Paper>
          </Grid>
          <Grid item sm={12} lg={6}>
            <Paper style={{ padding: 50 }}>
              <Typography
                variant="h4"
                component="h2"
                style={{ marginBottom: 20, marginLeft: 13 }}
              >
                Additional description
              </Typography>
              {ad && (
                <>
                  <List disablePadding>
                    <ListItem>
                      <ListItemText primary="Renting location" />
                      <Typography variant="subtitle1">
                        {ad.rentingCityLocation}, {ad.rentingStreetLocation}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Km restriction" />
                      <Typography variant="subtitle1">
                        {ad.kmRestriction}
                      </Typography>
                    </ListItem>
                    <Divider />
                    <ListItem>
                      <ListItemText primary="Price per day" />
                      <Typography variant="subtitle1">{ad.price}</Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Number of children seats" />
                      <Typography variant="subtitle1">
                        {ad.numberChildSeats}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Collision damage waiver?" />
                      <Typography variant="subtitle1">
                        {ad.collisionDamage ? "YES" : "NO"}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Agent/User email" />
                      <Typography variant="subtitle1">
                        agent@gmail.com
                      </Typography>
                    </ListItem>

                    <Divider />
                  </List>
                </>
              )}
            </Paper>
          </Grid>
        </Grid>
      </div>
    </div>
  );
}

export default withRouter(AdDetails);
