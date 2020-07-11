import React, { useState } from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import CardActions from "@material-ui/core/CardActions";
import AddLocationIcon from "@material-ui/icons/AddLocation";
import LocationOnIcon from "@material-ui/icons/LocationOn";
import AddAdvertisement from "../AddAdvertisement";
import AddCommentIcon from "@material-ui/icons/AddComment";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import { withRouter } from "react-router";
import MyLocationIcon from "@material-ui/icons/MyLocation";
import axios from "axios";

function CarCard({ generateToken, getExistingToken, car, history, user }) {
  const [openActiveAd, setOpenAcitveAd] = useState(-1);

  const showCarOnMap = async (car) => {
    try {
      const { data } = await axios.get(`/car/locationToken/${car.id}`);
      history.push({
        pathname: "/maps",
        state: { carToken: data, agentUsername: user.username },
      });
    } catch (err) {
      console.log(err);
    }
  };

  const handleSetOpen = (openState) => {
    if (!openState) {
      setOpenAcitveAd(-1);
    }
  };

  return (
    <Card raised>
      <CardHeader
        title={`${car.brandName} - ${car.modelName}`}
        subheader="September 14, 2016"
      />
      <CardMedia
        className="media"
        image={`/img${car.mainImageUrl}`}
        onClick={() => history.push(`/car/${car.id}`)}
        title={`${car.brandName} - ${car.modelName}`}
      />
      <CardActions disableSpacing className="cardFooter">
        <IconButton
          onClick={() => {
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
          <>
            <IconButton onClick={() => getExistingToken(car.id)}>
              <LocationOnIcon />
            </IconButton>
            <IconButton onClick={() => showCarOnMap(car)}>
              <MyLocationIcon />
            </IconButton>
          </>
        ) : (
          <IconButton
            style={{ color: "#4caf50" }}
            onClick={() => generateToken(car.id)}
          >
            <AddLocationIcon />
          </IconButton>
        )}
        <Button
          size="small"
          color="primary"
          onClick={() => history.push(`/car/${car.id}`)}
        >
          View car
        </Button>
      </CardActions>
    </Card>
  );
}

export default withRouter(CarCard);
