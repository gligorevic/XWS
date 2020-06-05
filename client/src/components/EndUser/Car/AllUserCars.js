import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { getCars, setTokenGenerated } from "../../../store/actions/cars";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import CardActions from "@material-ui/core/CardActions";
import IconButton from "@material-ui/core/IconButton";
import { makeStyles } from "@material-ui/core/styles";
import AddIcon from "@material-ui/icons/Add";
import AddLocationIcon from "@material-ui/icons/AddLocation";
import LocationOnIcon from "@material-ui/icons/LocationOn";
import Tooltip from "@material-ui/core/ToolTip";
import AddAdvertisement from "../AddAdvertisement";
import "./AllUserCars.css";
import axios from "axios";
import AddCommentIcon from "@material-ui/icons/AddComment";
import CopyTokenToClipBoard from "./CopyTokenToClipBoard";

const useStyles = makeStyles((theme) => ({
  card: {
    minHeight: 365,
  },
}));

const AllUserCars = ({
  getCars,
  cars,
  setAddCarDialogOpened,
  setTokenGenerated,
}) => {
  const [open, setOpened] = useState(false);
  const [token, setToken] = useState(null);
  const [openActiveAd, setOpenAcitveAd] = useState(-1);
  const classes = useStyles();

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

  const handleSetOpen = (openState) => {
    if (!openState) {
      setOpenAcitveAd(-1);
    }
  };

  return (
    <div style={{ overflow: "hidden", height: "100vh" }}>
      <Typography variant="h5">My cars</Typography>
      <Divider style={{ marginBottom: 40 }} />
      <Grid container spacing={3}>
        {cars.length > 0 &&
          cars.map((car) => (
            <Grid item sm={12} md={3}>
              <Card className={classes.card} raised>
                <CardHeader
                  title={`${car.brandName} - ${car.modelName}`}
                  subheader="September 14, 2016"
                />
                <CardMedia
                  className="media"
                  image="https://images.unsplash.com/photo-1542362567-b07e54358753?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
                  title={`${car.brandName} - ${car.modelName}`}
                />
                <CardActions disableSpacing className="cardFooter">
                  <IconButton
                    onClick={() => {
                      console.log(openActiveAd);
                      console.log(car.id === openActiveAd);
                      setOpenAcitveAd(car.id);
                    }}
                  >
                    <AddCommentIcon />
                  </IconButton>
                  {openActiveAd === car.id && (
                    <AddAdvertisement
                      carId={car.id}
                      open={openActiveAd === car.id}
                      setOpen={handleSetOpen}
                    />
                  )}
                  {car.tokenGenerated ? (
                    <IconButton onClick={() => getExistingToken(car.id)}>
                      <LocationOnIcon />
                    </IconButton>
                  ) : (
                    <IconButton
                      style={{ color: "#4caf50" }}
                      onClick={() => generateToken(car.id)}
                    >
                      <AddLocationIcon />
                    </IconButton>
                  )}
                </CardActions>
              </Card>
            </Grid>
          ))}
        <Grid item sm={12} md={3}>
          <Card
            className={open && "scaleOnClick"}
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
