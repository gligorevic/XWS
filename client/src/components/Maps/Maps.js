import React from "react";
import io from "socket.io-client";
import {
  Map,
  InfoWindow,
  Marker,
  GoogleApiWrapper,
  Circle,
} from "google-maps-react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";

let socket = null;

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  container: {
    padding: "10px 250px",
  },
}));

const Maps = (props) => {
  const classes = useStyles();
  const [currentLocation, setCurrentLocation] = React.useState();

  React.useEffect(() => {
    console.log(props);

    if (
      !props?.location?.state?.agentUsername ||
      !props?.location?.state?.carToken
    )
      props.history.push({ pathname: "/user", state: { tab: 5 } });
    else {
      socket = io("http://localhost:5000/");

      socket.emit(
        "location",
        {
          agentUsername: props?.location?.state?.agentUsername,
          carToken: props?.location?.state?.carToken,
        },
        (error) => {
          if (error) {
            alert(error);
          }
        }
      );

      socket.on(props?.location?.state?.agentUsername, (data) => {
        console.log(data);
        setCurrentLocation(data);
      });
    }
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
                props.history.push({ pathname: `/user`, state: { tab: 3 } })
              }
            >
              Go Back
            </Button>
          </span>
        </Toolbar>
      </AppBar>

      <Map
        style={{ width: "100%", height: "100%", position: "relative" }}
        google={props.google}
        center={currentLocation}
        zoom={18}
      >
        <Marker
          name={"Your position"}
          position={currentLocation}
          icon={{
            url: require("./locationIcon.png"),
            anchor: new props.google.maps.Point(32, 32),
            scaledSize: new props.google.maps.Size(32, 32),
          }}
        />

        <InfoWindow>
          <div>
            <h1>Heeej</h1>
          </div>
        </InfoWindow>
      </Map>
    </div>
  );
};

export default GoogleApiWrapper({
  apiKey: "AIzaSyDn-L1cXPIx-lcohTNqguvD_Areu0eaIyw",
  sensor: false,
})(Maps);
