import React, { useEffect } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import AppBar from "../../layouts/Navbar/NavbarCart";

import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";
import Button from "@material-ui/core/Button";

import CssBaseline from "@material-ui/core/CssBaseline";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import IconButton from "@material-ui/core/IconButton";
import clsx from "clsx";
import Axios from "axios";

import { getAdvertisementsForCart } from "../../../store/actions/advertisement";

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
}));

const CartPage = ({ allAdvertisementsCart, getAdvertisementsForCart }) => {
  useEffect(() => {
    var cartList = JSON.parse(localStorage.getItem("Cart"));
    console.log(cartList);
    getAdvertisementsForCart(cartList);
  }, []);

  const [open, setOpen] = React.useState(false);
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

  const handleDrawerClose = () => {
    setOpen(false);
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
          <div>
            <List
              component="nav"
              aria-label="main mailbox folders"
              style={{
                width: 500,
                height: 400,
                overflowY: "scroll",
              }}
            >
              {allAdvertisementsCart &&
                allAdvertisementsCart.map((ad) => {
                  return (
                    <>
                      <ListItem>
                        <ListItemText
                          primary={ad.brandName + " - " + ad.modelName}
                        />
                        <Button
                          onClick={(event) => handleSubmitRequest(event, ad)}
                          color="primary"
                        >
                          Send request
                        </Button>
                      </ListItem>
                    </>
                  );
                })}
            </List>
          </div>
        </main>
      </div>
    </>
  );
};

const mapStateToProps = (state) => ({
  allAdvertisementsCart: state.advertisement.allAdvertisementsCart,
});

export default withRouter(
  connect(mapStateToProps, { getAdvertisementsForCart })(CartPage)
);
