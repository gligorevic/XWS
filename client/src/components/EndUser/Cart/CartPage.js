import React, { useEffect } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import Divider from "@material-ui/core/Divider";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "../../layouts/Navbar/NavbarCart";

import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";

import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import IconButton from "@material-ui/core/IconButton";
import Axios from "axios";

import {
  getAdvertisementsForCart,
  setAllAdvertisementsForCart,
} from "../../../store/actions/advertisement";
import { setCartNum } from "../../../store/actions/user";

const useStyles = makeStyles((theme) => ({
  paddingMain: {
    padding: "100px 80px",
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
}));

const CartPage = ({
  allAdvertisementsCart,
  getAdvertisementsForCart,
  user,
  setCartNum,
}) => {
  const [cartList, setCartList] = React.useState([]);
  const [priceMap, setPriceMap] = React.useState({});

  useEffect(() => {
    setCartList(JSON.parse(localStorage.getItem("Cart") || "[]"));
    getAdvertisementsForCart(
      JSON.parse(localStorage.getItem("Cart") || "[]").map(
        (cartItem) => cartItem.id
      )
    );
  }, []);
  useEffect(() => {
    const newPriceMap = {};
    if (allAdvertisementsCart.length != 0) {
      allAdvertisementsCart.forEach(async (ad) => {
        newPriceMap[ad.id] = await getPriceForAd(ad);
      });
    }
    setPriceMap(newPriceMap);
  }, [allAdvertisementsCart]);

  const [loading, setLoading] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

  const [open, setOpen] = React.useState(false);
  const [bundle, setBundle] = React.useState([]);
  const [bundleRequest, setBundleRequest] = React.useState({
    userEmail: "",
    userSentRequest: user.user.username,
    requestDTOS: [],
  });
  const classes = useStyles();
  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleSubmitRequest = async (event, ad) => {
    event.preventDefault();
    setLoading(true);
    console.log(cartList.find((item) => item.id === ad.id));
    var cartItem = cartList.find((item) => item.id === ad.id);
    cartItem["userEmail"] = ad.userEmail;
    cartItem["userSentRequest"] = user.user.username;
    cartItem["brandName"] = ad.brandName;
    cartItem["modelName"] = ad.modelName;

    const res = await Axios.post(`/pricelist-service/price`, {
      dateFrom: cartItem.freeFrom,
      dateTo: cartItem.freeTo,
      adId: cartItem.id,
    });
    cartItem["finalPrice"] = res.data;

    const resp = await Axios.post(`/request`, cartItem).catch((error) => {
      if (error.response && error.response.status === 400) {
        setLoading(false);
        setOpenFailure(true);
        setErrorMessage(error.response.data);
      }
    });

    if (resp && resp.status >= 200 && resp.status < 300) {
      setLoading(false);
      setOpenSuccess(true);

      const filteredCart = cartList.filter((cartItem) => cartItem.id !== ad.id);

      setCartList(filteredCart);

      setCartNum(filteredCart.length);
      localStorage.setItem(
        "Cart",
        JSON.stringify(filteredCart.filter((cartItem) => cartItem.id !== ad.id))
      );
      getAdvertisementsForCart(filteredCart.map((cartItem) => cartItem.id));
    }
  };

  const handleSendBundleRequest = async (e) => {
    e.preventDefault();
    setLoading(true);

    bundleRequest.requestDTOS = bundle;
    const resp = await Axios.post(`/request/bundle`, bundleRequest).catch(
      (error) => {
        if (error.response && error.response.status === 400) {
          setLoading(false);
          setOpenFailure(true);
          setErrorMessage(error.response.data);
        }
      }
    );

    if (resp && resp.status >= 200 && resp.status < 300) {
      setLoading(false);
      setOpenSuccess(true);

      let cartListItemsFiltered;
      bundle.map(async (bundleItem) => {
        cartListItemsFiltered = cartList.filter(
          (cartItem) => cartItem.id !== bundleItem.id
        );
      });
      setCartList(cartListItemsFiltered);
      setCartNum(cartListItemsFiltered.length);
      getAdvertisementsForCart(
        cartListItemsFiltered.map((cartItem) => cartItem.id)
      );
      localStorage.setItem("Cart", JSON.stringify(cartListItemsFiltered));
      setBundle([]);
    }
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseError = () => {
    setOpenFailure(false);
  };

  const handleAddToBundle = (e, ad) => {
    e.preventDefault();

    var cartItem = cartList.filter((item) => item.id === ad.id);
    cartItem[0]["userEmail"] = ad.userEmail;
    cartItem[0]["userSentRequest"] = user.user.username;
    cartItem[0]["brandName"] = ad.brandName;
    cartItem[0]["modelName"] = ad.modelName;

    setBundle((oldState) => [...oldState, cartItem[0]]);
    bundleRequest.userEmail = ad.userEmail;
  };

  const handleRemoveFromBundle = (e, ad) => {
    e.preventDefault();
    setBundle((oldState) => oldState.filter((adBundle) => adBundle !== ad));
  };

  const handleRemoveFromCart = (e, ad) => {
    e.preventDefault();
    const filteredCart = cartList.filter((c) => c.id !== ad.id);
    setCartList(filteredCart);
    setCartNum(filteredCart.length);
    localStorage.setItem("Cart", JSON.stringify(filteredCart));
    getAdvertisementsForCart(filteredCart);
  };

  const drawer = (
    <div>
      <div className={classes.drawerHeader}>
        <IconButton onClick={handleDrawerClose}>
          <ChevronLeftIcon />
        </IconButton>
      </div>
      <Divider />
      <div className={classes.toolbar} />
    </div>
  );

  const getPriceForAd = async (ad) => {
    const cartItem = cartList.find((item) => item.id === ad.id);

    if (!!cartItem) {
      const { data } = await Axios.post(`/pricelist-service/price`, {
        dateFrom: cartItem.freeFrom,
        dateTo: cartItem.freeTo,
        adId: cartItem.id,
      });

      return data;
    }
    return 0;
  };

  const [hovered, setHovered] = React.useState();
  const handleOnMouseOver = (ad) => {
    setHovered(ad.id);
  };

  return (
    <>
      <div className={classes.root}>
        <AppBar open={open} handleDrawerOpen={handleDrawerOpen} />

        <main className={classes.paddingMain}>
          <div className={classes.drawerHeader} />
          <Grid container spacing={1}>
            <Grid item md={8} className={classes.listContainer}>
              <h2>My Cart Items</h2>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{
                  height: 400,
                  overflowY: "scroll",
                }}
              >
                {allAdvertisementsCart &&
                  allAdvertisementsCart.map((ad) => {
                    return (
                      <>
                        <ListItem>
                          <Grid
                            container
                            justify="space-around"
                            onMouseEnter={() => handleOnMouseOver(ad)}
                            onMouseLeave={() => setHovered()}
                          >
                            <Grid item className={classes.listContainer} sm={2}>
                              <ListItemText
                                primary={ad.brandName + " - " + ad.modelName}
                              />
                            </Grid>
                            <Grid
                              item
                              style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                              }}
                              sm={1}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            <Grid item className={classes.listContainer} sm={2}>
                              <ListItemText primary={ad.userEmail} />
                            </Grid>
                            <Grid
                              item
                              style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                              }}
                              sm={1}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            {hovered === ad.id && (
                              <Grid item sm={1}>
                                <div
                                  style={{
                                    display: "inline-block",
                                    height: "80%",
                                    width: "80%",
                                    margin: "10%",
                                    display: "flex",
                                    justifyContent: "center",
                                    aligntItems: "center",
                                    background: "#ff9800",
                                    color: "white",
                                    borderRadius: "50%",
                                  }}
                                >
                                  <p>{priceMap[ad.id]}€</p>
                                </div>
                              </Grid>
                            )}
                            <Grid
                              item
                              className={classes.listContainer}
                              sm={5}
                              style={{
                                display: "flex",
                                justifyContent: "space-between",
                              }}
                            >
                              <Button
                                onClick={(event) =>
                                  handleSubmitRequest(event, ad)
                                }
                                color="primary"
                                size="small"
                                variant="contained"
                              >
                                Send request
                              </Button>

                              <Button
                                onClick={(event) =>
                                  handleAddToBundle(event, ad)
                                }
                                disabled={
                                  bundle.length < 1
                                    ? false
                                    : bundleRequest.userEmail !== ad.userEmail
                                    ? true
                                    : bundle.filter((item) => item.id === ad.id)
                                        .length > 0
                                }
                                variant="contained"
                                size="small"
                              >
                                Add to bundle
                              </Button>

                              <Button
                                onClick={(event) =>
                                  handleRemoveFromCart(event, ad)
                                }
                                disabled={
                                  bundle.length < 1
                                    ? false
                                    : bundle.filter((item) => item.id === ad.id)
                                        .length > 0
                                }
                                variant="contained"
                                size="small"
                                style={{ backgroundColor: "#ffaaaa" }}
                              >
                                Remove
                              </Button>
                            </Grid>
                          </Grid>
                        </ListItem>
                      </>
                    );
                  })}
              </List>
            </Grid>
            <Grid item md={4} className={classes.listContainer}>
              <h2>Bundle List</h2>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{
                  width: 500,
                  height: 400,
                  overflowY: "scroll",
                }}
              >
                {bundle.length > 0 &&
                  bundle.map((ad) => {
                    return (
                      <>
                        <ListItem>
                          <ListItemText
                            primary={ad.brandName + " - " + ad.modelName}
                          />

                          <Button
                            style={{ float: "right" }}
                            onClick={(event) =>
                              handleRemoveFromBundle(event, ad)
                            }
                            variant="contained"
                            size="small"
                            color="secundary"
                          >
                            Remove from bundle
                          </Button>
                        </ListItem>
                      </>
                    );
                  })}
                <ListItem className={classes.listContainer}>
                  <Button
                    onClick={(event) => handleSendBundleRequest(event)}
                    disabled={bundle.length < 2}
                    variant="contained"
                    color="secundary"
                  >
                    Send bundle request
                  </Button>
                </ListItem>
              </List>
            </Grid>
          </Grid>
        </main>
      </div>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>

      <Snackbar
        open={openSuccess}
        autoHideDuration={2000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Successfully sent.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openFailure}
        autoHideDuration={2000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </>
  );
};

const mapStateToProps = (state) => ({
  user: state.user,
  allAdvertisementsCart: state.advertisement.allAdvertisementsCart,
});

export default withRouter(
  connect(mapStateToProps, {
    getAdvertisementsForCart,
    setAllAdvertisementsForCart,
    setCartNum,
  })(CartPage)
);
