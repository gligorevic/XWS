import React, { useState } from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import cadillacImage from "../images/cadillac.jpg";

import { makeStyles } from "@material-ui/core/styles";

import Advertisements from "./Advertisements";
/*import Search from "../Search/Search";*/

const useStyles = makeStyles((theme) => ({
  back: {
    backgroundImage:
      "linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url(" +
      cadillacImage +
      ")",
    backgroundRepeat: "no-repeat",
    backgroundSize: "cover",
    height: "100vh",
  },
  center: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
  },
}));

const Home = () => {
  const classes = useStyles();

  const twoDaysFromNow = new Date(
    new Date().getTime() + 1000 * 60 * 60 * 24 * 2
  );

  const [selectedStartDate, setSelectedStartDate] = useState(twoDaysFromNow);
  const [selectedEndDate, setSelectedEndDate] = useState(twoDaysFromNow);

  return (
    <div className={classes.back}>
      <MainNavbar />
      <div className={classes.center}>
        <Advertisements
          selectedStartDate={selectedStartDate}
          selectedEndDate={selectedEndDate}
        />
      </div>
    </div>
  );
};

export default Home;
