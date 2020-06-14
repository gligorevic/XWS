import React, { useEffect } from "react";
import { getAllAdvertisements } from "../../store/actions/advertisement";
import { increaseCartNum } from "../../store/actions/user";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";
import { makeStyles } from "@material-ui/core/styles";
import { connect } from "react-redux";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
import IconButton from "@material-ui/core/IconButton";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import LocalOfferIcon from "@material-ui/icons/LocalOffer";
import "./Avertisements.css";
import ViewDetails from "../Dialogs/AdvertisementDetails";

const useStyles = makeStyles((theme) => ({
  media: {
    padding: "52%",
  },
  root: {
    maxWidth: 1000,
  },
  card: {
    boxShadow: "0px 2px 23px 2px rgba(0,0,0,0.75)",
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
}));

const Advertisements = ({
  ads,
  user: { user },
  getAllAdvertisements,
  increaseCartNum,
  selectedStartDate,
  selectedEndDate,
}) => {
  const classes = useStyles();

  useEffect(() => {
    getAllAdvertisements();
  }, []);

  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openInfo, setOpenInfo] = React.useState(false);

  const handleAddToCart = (event, adId) => {
    event.preventDefault();
    let cartState = JSON.parse(localStorage.getItem("Cart")) || [];
    var cartItem = {
      id: adId,
      freeFrom: selectedStartDate,
      freeTo: selectedEndDate,
    };

    if (cartState.filter((item) => item.id === adId).length > 0) {
      setOpenInfo(true);
      return;
    }

    cartState = [...cartState, cartItem];
    localStorage.setItem("Cart", JSON.stringify(cartState));
    increaseCartNum();
    setOpenSuccess(true);
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseInfo = () => {
    setOpenInfo(false);
  };

  return (
    <>
      <Grid
        container
        spacing={3}
        justify="space-between"
        alignItems="center"
        className={classes.root}
      >
        {ads &&
          ads.map((row) => {
            return (
              <Grid item sm={12} md={4}>
                <Card className={classes.card}>
                  <CardHeader
                    action={
                      <IconButton
                        aria-label="settings"
                        className="priceToggler"
                      >
                        <LocalOfferIcon className="priceButton" />
                        <span className="purePrice">
                          {row.price ? row.price : row.priceFrom}
                        </span>
                      </IconButton>
                    }
                    title={`${
                      row.brandName ? row.brandName : row.car.brand.brandName
                    } - ${
                      row.modelName ? row.modelName : row.car.model.modelName
                    }`}
                    subheader="September 14, 2016"
                  />
                  <CardMedia
                    className={classes.media}
                    image={`/images/${row.id}/${row.mainImagePath}`}
                    title="Paella dish"
                  />
                  <CardContent></CardContent>
                  <CardActions>
                    <ViewDetails id={row.id} />
                    {user.role &&
                      user.role.some((r) => r.name === "ROLE_ENDUSER") && (
                        <IconButton
                          onClick={(event) => handleAddToCart(event, row.id)}
                          color="primary"
                          aria-label="add to shopping cart"
                        >
                          <AddShoppingCartIcon />
                        </IconButton>
                      )}
                  </CardActions>
                </Card>
              </Grid>
            );
          })}
      </Grid>
      <Snackbar
        open={openSuccess}
        autoHideDuration={3000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Successfully added to your cart.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openInfo}
        autoHideDuration={3000}
        onClose={handleCloseInfo}
      >
        <Alert onClose={handleCloseInfo} severity="info">
          This car is already in your cart.
        </Alert>
      </Snackbar>
    </>
  );
};

const mapStateToProps = (state) => ({
  ads: state.advertisement.allAdvertisements,
  user: state.user,
});

export default connect(mapStateToProps, {
  getAllAdvertisements,
  increaseCartNum,
})(Advertisements);
