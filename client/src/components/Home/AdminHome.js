import React from "react";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import AppBar from "../layouts/Navbar/Navbar";

import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import CssBaseline from "@material-ui/core/CssBaseline";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import IconButton from "@material-ui/core/IconButton";
import clsx from "clsx";
import CertificatesList from "../Admin/CertificatesList";
import RevocatedCertsList from "../Admin/RevocatedCertsList";
import TabPanel from "../layouts/TabPanel";
import ManipulateCarInfo from "../Admin/CarInfoService/ManipulateCarInfo";
import ManipulatePrivileges from "../Admin/UserPrivilegesManipulation/ManipulatePrivileges";
import CommentRequestsList from "../Admin/CommentManipulation/CommentRequestsList";
import ChangePassword from "../PasswordChange/PasswordChange";
import CompanyRequests from "../Admin/CompanyRequests";

import Profile from "../Pages/Profile";

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

function AdminHome(props) {
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
          {theme.direction === "ltr" ? (
            <ChevronLeftIcon />
          ) : (
            <ChevronRightIcon />
          )}
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
        <Tab label="List of certificates" {...a11yProps(0)} />
        <Tab label="Revocated certificates" {...a11yProps(1)} />
        <Tab label="Profile" {...a11yProps(2)} />
        <Tab label="Manipulate privileges" {...a11yProps(3)} />
        <Tab label="Manipulate car info" {...a11yProps(4)} />
        <Tab label="Comment requests" {...a11yProps(5)} />
        <Tab label="Change password" {...a11yProps(6)} />
        <Tab label="Company requests" {...a11yProps(7)} />
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
            {value === 0 && <CertificatesList />}
          </TabPanel>
          <TabPanel value={value} index={1}>
            {value === 1 && <RevocatedCertsList />}
          </TabPanel>
          <TabPanel value={value} index={2}>
            {value === 2 && <Profile />}
          </TabPanel>
          <TabPanel value={value} index={3}>
            {value === 3 && <ManipulatePrivileges />}
          </TabPanel>
          <TabPanel value={value} index={4}>
            {value === 4 && <ManipulateCarInfo />}
          </TabPanel>
          <TabPanel value={value} index={5}>
            {value === 5 && <CommentRequestsList />}
          </TabPanel>
          <TabPanel value={value} index={6}>
            {value === 6 && <ChangePassword />}
          </TabPanel>
          <TabPanel value={value} index={7}>
            {value === 7 && <CompanyRequests />}
          </TabPanel>
        </main>
      </div>
    </>
  );
}

export default AdminHome;
