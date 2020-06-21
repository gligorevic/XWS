import React, { useState } from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import cadillacImage from "../images/cadillac.jpg";

import { makeStyles } from "@material-ui/core/styles";

import Advertisements from "./Advertisements";
import Search from "../Search/Search";
import { withRouter } from "react-router";

const useStyles = makeStyles((theme) => ({
  back: {
    backgroundImage:
      "linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url(" +
      cadillacImage +
      ")",
    backgroundAttachment: "fixed",
    backgroundRepeat: "no-repeat",
    backgroundSize: "cover",
    height: "100%",
  },
  center: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
  },
}));

const Home = ({ history }) => {
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
        <Search
          selectedStartDate={selectedStartDate}
          setSelectedStartDate={setSelectedStartDate}
          selectedEndDate={selectedEndDate}
          setSelectedEndDate={setSelectedEndDate}
        />
        <Advertisements
          selectedStartDate={selectedStartDate}
          selectedEndDate={selectedEndDate}
          history={history}
        />
      </div>
    </div>
  );
};

export default withRouter(Home);
