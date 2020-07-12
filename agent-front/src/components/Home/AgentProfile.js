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
import CarPage from "../Car/CarPage";
import ListAdvertisements from "../Advertisement/ListAdvertisements";
import ListPassedRequests from "../Report/ListPassedRequests";
import StatisticTab from "../Statistic/StatisticTab";
import MessageTab from "../Message/MessageTab";
import PricelistPage from "../Pricelist/PricelistPage";
import RequestsPage from "../Request/RequestsPage";
import Synchronize from "../Synchronization/Synchronization";

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

function AgentProfile(props) {
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
        <Tab label="Cars" {...a11yProps(0)} />
        <Tab label="Advertisements" {...a11yProps(1)} />
        <Tab label="Reports" {...a11yProps(2)} />
        <Tab label="Statistic" {...a11yProps(3)} />
        <Tab label="Reserved requests" {...a11yProps(4)} />
        <Tab label="Pricelists" {...a11yProps(5)} />
        <Tab label="Requests" {...a11yProps(6)} />
        <Tab label="Synchronize" {...a11yProps(7)} />
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
            {value === 0 && <CarPage />}
          </TabPanel>
          <TabPanel value={value} index={1}>
            {value === 1 && <ListAdvertisements />}
          </TabPanel>
          <TabPanel value={value} index={2}>
            {value === 2 && <ListPassedRequests />}
          </TabPanel>
          <TabPanel value={value} index={3}>
            {value === 3 && <StatisticTab />}
          </TabPanel>
          <TabPanel value={value} index={4}>
            {value === 4 && <MessageTab />}
          </TabPanel>
          <TabPanel value={value} index={5}>
            {value === 5 && <PricelistPage />}
          </TabPanel>
          <TabPanel value={value} index={6}>
            {value === 6 && <RequestsPage />}
          </TabPanel>
          <TabPanel value={value} index={7}>
            {value === 7 && <Synchronize />}
          </TabPanel>
        </main>
      </div>
    </>
  );
}

export default AgentProfile;
