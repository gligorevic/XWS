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
import InfoIcon from "@material-ui/icons/Info";
import { Paper } from "@material-ui/core";
import LocationCityIcon from "@material-ui/icons/LocationCity";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Typography from "@material-ui/core/Typography";
import Rating from "@material-ui/lab/Rating";
import Tooltip from "@material-ui/core/Tooltip";

import FilterListIcon from "@material-ui/icons/FilterList";

const useStyles = makeStyles((theme) => ({
  media: {
    padding: "52%",
  },
  root: {
    maxWidth: 1080,
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
  const [sortBy, setSortBy] = React.useState("");
  const [sortMenuOpened, setSortMenuOpened] = React.useState(false);

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

  const [anchorEl, setAnchorEl] = React.useState(null);
  const handleOpenSortMenu = (event) => {
    setAnchorEl(event.currentTarget);
    setSortMenuOpened(true);
  };

  const sortBeforeRender = (ads) =>
    sortBy != ""
      ? ads.sort((a, b) => {
          if (sortBy === "Price") return a.price - b.price;
          else if (sortBy === "Kilometers") return a.kmPassed - b.kmPassed;
          else if (sortBy === "Rate") return b.avgRate - a.avgRate;
        })
      : ads;

  return (
    <>
      <Grid
        container
        spacing={3}
        justify="space-between"
        alignItems="center"
        className={classes.root}
      >
        {ads && ads.length > 0 && (
          <Grid item xs={12}>
            <IconButton
              style={{ color: "white", background: "rgba(0,0,0,0.2)" }}
              onClick={handleOpenSortMenu}
            >
              <FilterListIcon />
            </IconButton>
            {sortBy.length > 0 && (
              <span
                style={{
                  color: "white",
                  marginLeft: 10,
                  background: "rgba(0, 0, 0, 0.3)",
                  padding: 5,
                  borderRadius: "13%",
                }}
              >
                Sorted by: {sortBy}
              </span>
            )}
            <Menu
              anchorEl={anchorEl}
              open={sortMenuOpened}
              onClose={() => setSortMenuOpened(false)}
            >
              <MenuItem
                onClick={() => {
                  setSortBy("Price");
                  setSortMenuOpened(false);
                }}
              >
                Price
              </MenuItem>
              <MenuItem
                onClick={() => {
                  setSortBy("Rate");
                  setSortMenuOpened(false);
                }}
              >
                Rate
              </MenuItem>
              <MenuItem
                onClick={() => {
                  setSortBy("Kilometers");
                  setSortMenuOpened(false);
                }}
              >
                Kilometers
              </MenuItem>
            </Menu>
          </Grid>
        )}
        {ads && ads.length > 0 ? (
          sortBeforeRender(ads).map((row) => {
            return (
              <Grid item sm={12} md={4}>
                <Card className={classes.card}>
                  <CardHeader
                    action={
                      <IconButton
                        aria-label="settings"
                        className="priceToggler"
                      >
                        <LocalOfferIcon className="disapearOnHover" />
                        <span className="purePrice">
                          <span className="jump">{row.price} €</span>
                          <sub>
                            <span
                              style={{ fontSize: "115%", margin: "0px 2px" }}
                            >
                              /
                            </span>
                            day
                          </sub>
                        </span>
                      </IconButton>
                    }
                    title={`${row.brandName} - ${row.modelName}`}
                    subheader={
                      <span
                        style={{
                          display: "flex",
                          justifyContent: "flex-start",
                          alignItems: "center",
                          margin: "5px 0",
                        }}
                      >
                        <LocationCityIcon style={{ marginRight: 6 }} />{" "}
                        {row.cityName}
                      </span>
                    }
                  />
                  <CardMedia
                    className={classes.media}
                    image={`/img${row.mainImagePath}`}
                    title={row.brandName + " " + row.modelName}
                  />
                  <CardContent>
                    <Typography
                      variant="body2"
                      color="textSecondary"
                      component="p"
                    >
                      Agent: {row.agentUsername}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="textSecondary"
                      component="p"
                    >
                      Passed: {row.kmPassed}km
                    </Typography>
                    <Rating
                      name="half-rating-read"
                      value={row.avgRate}
                      precision={0.01}
                      readOnly
                    />
                  </CardContent>
                  <CardActions
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "center",
                    }}
                  >
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
          })
        ) : (
          <Paper
            style={{
              width: "100%",
              display: "flex",
              alignItems: "center",
              justifyContent: "flex-start",
              padding: 50,
              fontSize: 22,
              fontWeight: 500,
              color: "#616161",
            }}
          >
            <InfoIcon style={{ fontSize: 35, marginRight: 6 }} />
            <p>No advertisements for given params.</p>
          </Paper>
        )}
      </Grid>
      <Snackbar
        open={openSuccess}
        autoHideDuration={2000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Successfully added to your cart.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openInfo}
        autoHideDuration={2000}
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
