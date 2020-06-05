import React from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import ficaImage from "../../images/fica.jpg";

import { makeStyles } from "@material-ui/core/styles";

import Advertisements from "./Advertisements";
import Search from "../Search/Search";

const useStyles = makeStyles((theme) => ({
  back: {
    backgroundImage:
      "linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url(" +
      ficaImage +
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

  const handleAddToCart = (event, adId) => {
    event.preventDefault();
    let cartState = JSON.parse(localStorage.getItem("Cart")) || [];

    if (cartState === []) {
      cartState = [adId];
      localStorage.setItem("Cart", JSON.stringify(cartState));
    } else {
      if (cartState.includes(adId)) return;
      cartState = [...cartState, adId];
      localStorage.setItem("Cart", JSON.stringify(cartState));
    }
  };

  return (
    <div className={classes.back}>
      <MainNavbar />
      <div className={classes.center}>
        <Search />
        <Advertisements />
      </div>
    </div>
  );
};

export default Home;
