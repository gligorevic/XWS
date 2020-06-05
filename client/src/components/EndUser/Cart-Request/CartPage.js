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
    marginLeft: -drawerWidth,
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
    const resp = await Axios.post(`/request`, ad);

    if (resp.status === 201) {
      var cartList = JSON.parse(localStorage.getItem("Cart"));
      cartList.pop(ad.id);
      localStorage.setItem("Cart", JSON.stringify(cartList));
    }
  };

  const handleSendBundleRequest = async (e) => {
    bundleRequest.requestDTOS = bundle;
    console.log(bundleRequest);
    const resp = await Axios.post(`/request/bundle`, bundleRequest);

    if (resp.status === 201) {
      console.log("dodao bundle");
    }
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleAddToBundle = (e, ad) => {
    setBundle((oldState) => [...oldState, ad]);
    bundleRequest.userEmail = ad.userEmail;
    console.log(bundle);
  };

  const handleRemoveFromBundle = (e, ad) => {
    setBundle((oldState) => oldState.filter((adBundle) => adBundle !== ad));
    console.log(bundle);
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
          {/* The implementation can be swapped with js to avoid SEO duplication of links. */}

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
            <Grid item md={6} className={classes.listContainer}>
              <h2>My Cart Items</h2>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{
                  width: 700,
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
                            <Grid item md={2} className={classes.listContainer}>
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
                                width: 7,
                              }}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            <Grid item md={3} className={classes.listContainer}>
                              <ListItemText primary={ad.userEmail} />
                            </Grid>
                            <Grid
                              item
                              style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                width: 7,
                              }}
                            >
                              <Divider orientation="vertical" />
                            </Grid>
                            <Grid item md={3} className={classes.listContainer}>
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

                            <Grid item md={3} className={classes.listContainer}>
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
                                color="secundary"
                              >
                                Add to bundle
                              </Button>
                            </Grid>
                          </Grid>
                        </ListItem>
                      </>
                    );
                  })}
              </List>
            </Grid>
            <Grid item md={6} className={classes.listContainer}>
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
