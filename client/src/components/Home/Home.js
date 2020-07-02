import React, { useState } from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import ficaImage from "../../images/fica.jpg";

import { makeStyles } from "@material-ui/core/styles";

import Advertisements from "./Advertisements";
import Search from "../Search/Search";
import Footer from "../layouts/Footer";

const useStyles = makeStyles((theme) => ({
  back: {
    backgroundImage:
      "linear-gradient(rgba(255,255,255,0.45), rgba(255,255,255,0.45)), url(" +
      ficaImage +
      ")",
    backgroundAttachment: "fixed",
    backgroundRepeat: "no-repeat",
    backgroundSize: "cover",
    height: "100%",
    boxShadow: "0 0 38px 28px rgba(0,0,0,0.45) inset",
  },
  center: {
    display: "flex",
    justifyContent: "flex-start",
    alignItems: "center",
    flexDirection: "column",
    minHeight: "100vh",
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
        <Search
          selectedStartDate={selectedStartDate}
          setSelectedStartDate={setSelectedStartDate}
          selectedEndDate={selectedEndDate}
          setSelectedEndDate={setSelectedEndDate}
        />
        <Advertisements
          selectedStartDate={selectedStartDate}
          selectedEndDate={selectedEndDate}
        />
      </div>
      <Footer />
    </div>
  );
};

export default Home;
