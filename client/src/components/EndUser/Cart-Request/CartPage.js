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
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";

import CssBaseline from "@material-ui/core/CssBaseline";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import IconButton from "@material-ui/core/IconButton";
import clsx from "clsx";
import Axios from "axios";

import {
  getAdvertisementsForCart,
  setAllAdvertisementsForCart,
} from "../../../store/actions/advertisement";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: "flex-end",
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -290,
  },
  contentShift: {
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
  listContainer: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "column",
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
    var cartList = JSON.parse(localStorage.getItem("Cart"));
    console.log(cartList);
    getAdvertisementsForCart(cartList);
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
    const resp = await Axios.post(`/request`, ad).catch((error) => {
      if (error.response && error.response.status === 400) {
        setLoading(false);
        setOpenFailure(true);
        setErrorMessage(error.response.data);
      }
    });

    if (resp.status === 201) {
      setLoading(false);
      setOpenSuccess(true);
      var cartList = JSON.parse(localStorage.getItem("Cart"));
      cartList.pop(ad.id);
      localStorage.setItem("Cart", JSON.stringify(cartList));
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

    if (resp.status === 201) {
      setLoading(false);
      setOpenSuccess(true);
      var cartList = JSON.parse(localStorage.getItem("Cart"));
      cartList.pop([...bundle, bundle.id]);
      localStorage.setItem("Cart", JSON.stringify(cartList));
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
    setBundle((oldState) => [...oldState, ad]);
    bundleRequest.userEmail = ad.userEmail;
  };

  const handleRemoveFromBundle = (e, ad) => {
    setBundle((oldState) => oldState.filter((adBundle) => adBundle !== ad));
  };

  const handleRemoveFromCart = (e, ad) => {
    setBundle((oldState) => oldState.filter((adBundle) => adBundle !== ad));
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
        <CssBaseline />
        <AppBar open={open} handleDrawerOpen={handleDrawerOpen} />
        <nav className={classes.drawer} aria-label="mailbox folders">
          <Drawer
            className={classes.drawer}
            variant="persistent"
            anchor="left"
            open={open}
            classes={{
              paper: classes.drawerPaper,
            }}
          >
            {drawer}
          </Drawer>
        </nav>
        <main
          className={clsx(classes.content, {
            [classes.contentShift]: open,
          })}
        >
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
                          <Grid container spacing={1}>
                            <Grid
                              item
                              width={50}
                              className={classes.listContainer}
                            >
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
                                width: 30,
                              }}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            <Grid
                              item
                              width={100}
                              className={classes.listContainer}
                            >
                              <ListItemText primary={ad.userEmail} />
                            </Grid>
                            <Grid
                              item
                              style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                width: 30,
                              }}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            <Grid
                              item
                              width={150}
                              className={classes.listContainer}
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
                            </Grid>

                            <Grid
                              item
                              width={150}
                              className={classes.listContainer}
                            >
                              <Button
                                onClick={(event) =>
                                  handleAddToBundle(event, ad)
                                }
                                disabled={
                                  bundle.length < 1
                                    ? false
                                    : bundleRequest.userEmail !== ad.userEmail
                                    ? true
                                    : bundle.includes(ad)
                                }
                                variant="contained"
                                size="small"
                              >
                                Add to bundle
                              </Button>
                            </Grid>
                            <Grid
                              item
                              width={100}
                              className={classes.listContainer}
                            >
                              <Button
                                onClick={(event) =>
                                  handleRemoveFromCart(event, ad)
                                }
                                disabled={
                                  bundle.length < 1
                                    ? false
                                    : bundle.includes(ad)
                                    ? true
                                    : false
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
                          <Grid container spacing={3}>
                            <Grid item md={6} className={classes.listContainer}>
                              <ListItemText
                                primary={ad.brandName + " - " + ad.modelName}
                              />
                            </Grid>

                            <Grid item md={6} className={classes.listContainer}>
                              <Button
                                onClick={(event) =>
                                  handleRemoveFromBundle(event, ad)
                                }
                                variant="contained"
                                size="small"
                                color="secundary"
                              >
                                Remove from bundle
                              </Button>
                            </Grid>
                          </Grid>
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
      <Dialog
        open={openSuccess}
        keepMounted
        onClose={handleCloseSuccess}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="success">Successfully sent.</Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseSuccess} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        open={openFailure}
        keepMounted
        onClose={handleCloseError}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="error">{errorMessage}</Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseError} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
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
