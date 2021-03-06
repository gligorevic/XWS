import React from "react";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import Popper from "./Popper";

import useMediaQuery from "@material-ui/core/useMediaQuery";

import clsx from "clsx";
import Drawer from "@material-ui/core/Drawer";
import useScrollTrigger from "@material-ui/core/useScrollTrigger";
import List from "@material-ui/core/List";
import Slide from "@material-ui/core/Slide";
import Divider from "@material-ui/core/Divider";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { logout } from "../../../store/actions/auth";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import DirectionsCarIcon from "@material-ui/icons/DirectionsCar";
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart";
import "./MainNavbar.css";
import Axios from "axios";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
    display: "flex",
    alignItems: "center",
  },
  appBar: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  hide: {
    display: "none",
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
  username: {
    fontSize: 15,
    fontWeight: "bold",
    marginLeft: 20,
    marginRight: 5,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
}));

function HideOnScroll({ children }) {
  const trigger = useScrollTrigger();

  return (
    <Slide appear={false} direction="down" in={!trigger}>
      {children}
    </Slide>
  );
}

const MainNavbar = ({
  history,
  location,
  logout,
  user: { isAuthenticated, user, cartItemsNum },
}) => {
  const classes = useStyles();
  const theme = useTheme();
  const tablet = useMediaQuery(theme.breakpoints.up("sm"));

  const [open, setOpen] = React.useState(false);
  const [popupClosed, setPopupClosed] = React.useState(true);
  const [companyStatus, setCompanyStatus] = React.useState("Not Defined");
  React.useEffect(() => {
    (async () => {
      console.log(user.id);
      const res = await Axios.get(`auth/user/${user.id}/company/status`);
      console.log(res);
      if (res.status === 200) setCompanyStatus(res.data);
      setTimeout(() => setPopupClosed(false), 2000);
    })();
  }, []);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  window.addEventListener("localStorage", (e) => {
    console.log("asdasdasdasd");
    console.log(e);
  });

  return (
    <div className={classes.root}>
      <div style={{ marginTop: 40 }} />
      <HideOnScroll>
        <AppBar>
          <Toolbar className={classes.navContainer}>
            {!tablet && (
              <IconButton
                color="inherit"
                aria-label="open drawer"
                onClick={handleDrawerOpen}
                edge="start"
                className={clsx(classes.menuButton, open && classes.hide)}
              >
                <MenuIcon />
              </IconButton>
            )}

            <Typography variant="h6" className={classes.title}>
              <DirectionsCarIcon
                style={{ fontSize: 32, paddingBottom: 5, marginRight: 4 }}
              />
              Rentaj care
            </Typography>
            {tablet && (
              <>
                <Button
                  color="inherit"
                  onClick={() => location.pathname !== "/" && history.push("/")}
                >
                  Home
                </Button>
                {!isAuthenticated ? (
                  <>
                    <Button
                      color="inherit"
                      onClick={() =>
                        location.pathname !== "/login" && history.push("/login")
                      }
                    >
                      Login
                    </Button>
                    <Button
                      color="inherit"
                      onClick={() =>
                        location.pathname !== "/registration" &&
                        history.push("/registration")
                      }
                    >
                      Registrate
                    </Button>
                  </>
                ) : (
                  <>
                    <div style={{ position: "relative" }}>
                      <Button
                        style={{
                          postion: "absolute",
                          bottom: "50%",
                          left: "50%",
                          transform: "translateX(-50%)",
                        }}
                        color="inherit"
                        onClick={() => {
                          switch (user.role.length > 0 && user.role[0].name) {
                            case "ROLE_ADMIN":
                              history.push("/admin");
                              break;
                            case "ROLE_ENDUSER":
                            case "ROLE_AGENT":
                              history.push("/user");
                              break;
                          }
                        }}
                      >
                        Profile
                      </Button>
                      <Popper
                        open={companyStatus === "DENIED" && !popupClosed}
                        setPopupClosed={setPopupClosed}
                      />
                      {companyStatus === "DENIED" && !popupClosed && (
                        <>
                          <div className="pulsingDiv pulsingDiv1"></div>
                          <div className="pulsingDiv pulsingDiv2"></div>
                          <div className="pulsingDiv pulsingDiv3"></div>
                        </>
                      )}
                    </div>
                    <div className={classes.username}>
                      <AccountCircleIcon />
                      <p style={{ paddingLeft: 5 }}>{user.username}</p>
                    </div>
                    {user.role[0].name === "ROLE_ENDUSER" && (
                      <span
                        color="inherit"
                        onClick={() =>
                          location.pathname !== "/cart" && history.push("/cart")
                        }
                        style={{
                          position: "relative",
                          display: "inline-block",
                          width: 30,
                          height: 30,
                          margin: "0px 10px",
                          cursor: "pointer",
                        }}
                      >
                        <span
                          style={{
                            position: "absolute",
                            top: -8,
                            right: -8,
                            fontSize: 14,
                            borderRadius: 50,
                            background: "#ff9800dd",
                            width: 19,
                            zIndex: 3,
                            textAlign: "center",
                            color: "white",
                            fontWeight: 700,
                          }}
                        >
                          {cartItemsNum}
                        </span>
                        <ShoppingCartIcon
                          style={{
                            position: "absolute",
                            fontSize: 30,
                          }}
                        />
                      </span>
                    )}
                    <Button
                      color="inherit"
                      onClick={() => {
                        logout();
                        history.push("/");
                      }}
                    >
                      <ExitToAppIcon />
                      Logout
                    </Button>
                  </>
                )}
              </>
            )}
          </Toolbar>
        </AppBar>
      </HideOnScroll>
      {!tablet && (
        <Drawer
          className={classes.drawer}
          variant="persistent"
          anchor="left"
          open={open}
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          <div className={classes.drawerHeader}>
            <IconButton onClick={handleDrawerClose}>
              <ChevronLeftIcon />
            </IconButton>
          </div>
          <Divider />
          <List>
            <ListItem button>
              <ListItemIcon>
                <InboxIcon />
              </ListItemIcon>
              <ListItemText primary={"Home"} />
            </ListItem>
            <ListItem button>
              <ListItemIcon>
                <InboxIcon />
              </ListItemIcon>
              <ListItemText primary={"Profile"} />
            </ListItem>
            <ListItem button>
              <ListItemIcon>
                <InboxIcon />
              </ListItemIcon>
              <ListItemText primary={"Menu item"} />
            </ListItem>
            <Divider />
            <ListItem button>
              <ListItemIcon>
                <InboxIcon />
              </ListItemIcon>
              <ListItemText primary={"Login"} />
            </ListItem>
          </List>
        </Drawer>
      )}
    </div>
  );
};

function mapStateToProps(state) {
  return {
    user: state.user,
  };
}

export default withRouter(connect(mapStateToProps, { logout })(MainNavbar));
