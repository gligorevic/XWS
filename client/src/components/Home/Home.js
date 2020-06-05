import React from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import ficaImage from "../../images/fica.jpg";

import { makeStyles } from "@material-ui/core/styles";

import Advertisements from "./Advertisements";
import Search from "../Search/Search";

const useStyles = makeStyles({
  back: {
    background: "url(" + ficaImage + ")",
    height: "100%",
  },
});

const Home = () => {
  const classes = useStyles();
  return (
    <div className={classes.back}>
      <MainNavbar />
      <Search />
      <Advertisements />
    </div>
  );
};

export default Home;
