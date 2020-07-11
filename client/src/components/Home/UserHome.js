import React from "react";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import AppBar from "../layouts/Navbar/Navbar";

import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import CssBaseline from "@material-ui/core/CssBaseline";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import IconButton from "@material-ui/core/IconButton";
import clsx from "clsx";
import TabPanel from "../layouts/TabPanel";

import Profile from "../Pages/Profile";
import AddCar from "../EndUser/AddCar";
import PricelistPage from "../EndUser/Pricelist/PricelistPage";
import ListMyAdvertisements from "../EndUser/ListMyAdvertisements";
import CarPage from "../EndUser/Car/CarPage";
import RequestsPage from "../EndUser/Request/RequestsPage";
import ChangePassword from "../PasswordChange/PasswordChange";
import { withRouter } from "react-router";
import CompanyRegistration from "../Agent/Company/CompanyRegistration";
import { connect } from "react-redux";
import ListPassedRequests from "../EndUser/Report/ListPassedRequests";
<<<<<<< HEAD
import StatisticTab from "../Agent/Statistic/StatisticTab";
import AddedPriceTab from "../EndUser/AdditionalPrice/AddedPriceTab";
=======
import CreatedRequests from "../EndUser/Request/CreatedRequests/CreatedRequests";
>>>>>>> origin/location-service

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
function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

function UserHome({ location, user }) {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);
  const classes = useStyles();
  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  React.useEffect(() => {
    location?.state?.tab && setValue(location.state.tab);
  }, []);

  const drawer = (
    <div>
      <div className={classes.drawerHeader}>
        <IconButton onClick={handleDrawerClose}>
          <ChevronLeftIcon />
        </IconButton>
      </div>
      <Divider />
      <div className={classes.toolbar} />
      <Divider />
      <Tabs
        orientation="vertical"
        value={value}
        onChange={handleChange}
        variant="scrollable"
        aria-label="simple tabs example"
      >
        <Tab label="User Home Page" {...a11yProps(0)} />
        <Tab label="Profile" {...a11yProps(1)} />
        <Tab label="Add car" {...a11yProps(2)} />
        <Tab label="Pricelists" {...a11yProps(3)} />
        <Tab label="My advertisements" {...a11yProps(4)} />
        <Tab label="Cars" {...a11yProps(5)} />
        <Tab label="Requests" {...a11yProps(6)} />
        <Tab label="Change password" {...a11yProps(7)} />
        <Tab label="Reports" {...a11yProps(8)} />
        <Tab label="Additional expences" {...a11yProps(9)} />
        {user?.role && user.role.some((r) => r.name === "ROLE_AGENT") && (
          <Tab label="Statistic" {...a11yProps(10)} />
        )}
        {user?.role && user.role.some((r) => r.name === "ROLE_AGENT") && (
          <Tab label="Reregister company" {...a11yProps(11)} />
        )}
<<<<<<< HEAD
=======
        <Tab label="Reports" {...a11yProps(9)} />
        <Tab label="Created Requests" {...a11yProps(10)} />
>>>>>>> origin/location-service
      </Tabs>
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
          <TabPanel value={value} index={0}>
            <p>User home page</p>
          </TabPanel>
          <TabPanel value={value} index={1}>
            {value === 1 && <Profile />}
          </TabPanel>
          <TabPanel value={value} index={2}>
            {value === 2 && <AddCar />}
          </TabPanel>
          <TabPanel value={value} index={3}>
            {value === 3 && <PricelistPage />}
          </TabPanel>
          <TabPanel value={value} index={4}>
            {value === 4 && <ListMyAdvertisements />}
          </TabPanel>
          <TabPanel value={value} index={5}>
            {value === 5 && <CarPage />}
          </TabPanel>
          <TabPanel value={value} index={6}>
            {value === 6 && <RequestsPage />}
          </TabPanel>
          <TabPanel value={value} index={7}>
            {value === 7 && <ChangePassword />}
          </TabPanel>

          <TabPanel value={value} index={8}>
            {value === 8 && <ListPassedRequests />}
          </TabPanel>
          <TabPanel value={value} index={9}>
            {value === 9 && <AddedPriceTab />}
          </TabPanel>
          {user?.role && user.role.some((r) => r.name === "ROLE_AGENT") && (
            <TabPanel value={value} index={10}>
              {value === 10 && <StatisticTab />}
            </TabPanel>
          )}
          {user?.role && user.role.some((r) => r.name === "ROLE_AGENT") && (
            <TabPanel value={value} index={11}>
              {value === 11 && <CompanyRegistration />}
            </TabPanel>
          )}
<<<<<<< HEAD
=======
          <TabPanel value={value} index={9}>
            {value === 9 && <ListPassedRequests />}
          </TabPanel>
          <TabPanel value={value} index={10}>
            {value === 10 && <CreatedRequests />}
          </TabPanel>
>>>>>>> origin/location-service
        </main>
      </div>
    </>
  );
}
const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default withRouter(connect(mapStateToProps, null)(UserHome));
