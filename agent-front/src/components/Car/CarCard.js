import React, { useState } from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import CardActions from "@material-ui/core/CardActions";
import AddLocationIcon from "@material-ui/icons/AddLocation";
import LocationOnIcon from "@material-ui/icons/LocationOn";
import AddAdvertisement from "../Advertisement/AddAdvertisement";
import AddCommentIcon from "@material-ui/icons/AddComment";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import { withRouter } from "react-router";

function CarCard({ generateToken, getExistingToken, car, history }) {
  const [openActiveAd, setOpenAcitveAd] = useState(-1);

  const handleSetOpen = (openState) => {
    if (!openState) {
      setOpenAcitveAd(-1);
    }
  };

  return (
    <Card raised>
      <CardHeader
        title={`${car.brandName ? car.brandName : car.brand.brandName} - ${
          car.modelName ? car.modelName : car.model.modelName
        }`}
        subheader="September 14, 2016"
      />
      <CardMedia
        className="media"
        image={
          car.brandName
            ? `/images/${car.id}/${car.mainImageUrl}`
            : `${car.mainImageUrl}`
        }
        onClick={() => history.push(`/car/${car.id}`)}
        title={`${car.brandName ? car.brandName : car.brand.brandName} - ${
          car.modelName ? car.modelName : car.model.modelName
        }`}
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
