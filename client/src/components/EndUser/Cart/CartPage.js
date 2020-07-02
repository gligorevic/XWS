import React, { useEffect } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
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
}) => {
  useEffect(() => {
    var cartList = JSON.parse(localStorage.getItem("Cart")) || [];
    getAdvertisementsForCart(cartList.map((cartItem) => cartItem.id));
  }, []);

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
    var cartList = JSON.parse(localStorage.getItem("Cart"));
    var cartItem = cartList.filter((item) => item.id === ad.id);
    cartItem[0]["userEmail"] = ad.userEmail;
    cartItem[0]["userSentRequest"] = user.user.username;
    const resp = await Axios.post(`/request`, cartItem[0]).catch((error) => {
      if (error.response && error.response.status === 400) {
        setLoading(false);
        setOpenFailure(true);
        setErrorMessage(error.response.data);
      }
    });

    if (resp && resp.status >= 200 && resp.status < 300) {
      setLoading(false);
      setOpenSuccess(true);

      cartList.map((cartItem) => {
        if (cartItem.id === ad.id)
          cartList.splice(cartList.indexOf(cartItem), 1);
      });
      localStorage.setItem("Cart", JSON.stringify(cartList));
      getAdvertisementsForCart(cartList.map((cartItem) => cartItem.id));
    }
  };

  const handleSendBundleRequest = async (e) => {
    e.preventDefault();
    setLoading(true);
    var cartList = JSON.parse(localStorage.getItem("Cart"));

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
      var cartList = JSON.parse(localStorage.getItem("Cart"));
      bundle.map((bundleItem) => {
        cartList.map((cartItem) => {
          if (cartItem.id === bundleItem.id)
            cartList.splice(cartList.indexOf(cartItem), 1);
        });
      });
      getAdvertisementsForCart(cartList.map((cartItem) => cartItem.id));
      localStorage.setItem("Cart", JSON.stringify(cartList));
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
    var cartList = JSON.parse(localStorage.getItem("Cart"));
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
    var cartList = JSON.parse(localStorage.getItem("Cart"));
    cartList.splice(cartList.indexOf(ad.id), 1);
    localStorage.setItem("Cart", JSON.stringify(cartList));
    getAdvertisementsForCart(cartList);
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

  return (
    <>
      <div className={classes.root}>
        <AppBar open={open} handleDrawerOpen={handleDrawerOpen}></AppBar>

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
                          <Grid container justify="space-around">
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
                            <Grid
                              item
                              className={classes.listContainer}
                              sm={6}
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
        autoHideDuration={3500}
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
  })(CartPage)
);
