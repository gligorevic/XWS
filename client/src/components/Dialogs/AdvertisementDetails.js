import React, { useEffect } from "react";
import { connect } from "react-redux";
import Button from "@material-ui/core/Button";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import Typography from "@material-ui/core/Typography";
import { Paper } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Axios from "axios";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";
import Gallery from "../EndUser/Car/Gallery";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  title: {
    marginLeft: theme.spacing(2),
    flex: 1,
    padding: 10,
  },
  datePicker: {
    padding: 10,
  },
}));

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const AdvertisementDetails = ({ id, agentUsername }) => {
  const classes = useStyles();
  const [ad, setAd] = React.useState(null);
  const [open, setOpen] = React.useState(false);
  const [comments, setComments] = React.useState([]);

  React.useEffect(() => {
    (async () => {
      if (open) {
        const res = await Axios.get(`/feedback/user/${agentUsername}/comment`);
        console.log(res);
        setComments(res.data);
      }
    })();
  }, [open]);

  const handleClickOpen = async (e) => {
    setOpen(true);
    setLoading(true);
    const resp = await Axios.get(`/search/${id}`);
    setLoading(false);
    if (resp.status === 200) {
      setAd(resp.data);
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  const [loading, setLoading] = React.useState(false);

  return (
    <div>
      <Button size="small" color="primary" onClick={handleClickOpen}>
        View more
      </Button>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
        style={{ zIndex: 40 }}
      >
        <AppBar>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={handleClose}
              aria-label="close"
            >
              <CloseIcon />
            </IconButton>
            <Typography variant="h6" className={classes.title}>
              Advertisement details
            </Typography>
          </Toolbar>
        </AppBar>
        <Grid
          container
          spacing={2}
          style={{
            padding: 60,
            paddingTop: 120,
          }}
        >
          <Grid item xs={12} style={{ position: "relative" }}>
            {ad && (
              <Gallery
                images={ad.images.map((img) => ({
                  source: "/img" + img,
                }))}
              />
            )}
          </Grid>
          <Grid item xs={6}>
            <Paper style={{ padding: 50 }}>
              <Typography
                variant="h4"
                component="h2"
                style={{ marginBottom: 20, marginLeft: 13 }}
              >
                Car
              </Typography>
              {ad && (
                <>
                  <List disablePadding>
                    <ListItem>
                      <ListItemText primary="Brand" />
                      <Typography variant="subtitle1">
                        {ad.brandName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Model" />
                      <Typography variant="subtitle1">
                        {ad.modelName}
                      </Typography>
                    </ListItem>
                    <Divider />
                    <ListItem>
                      <ListItemText primary="Gear shift" />
                      <Typography variant="subtitle1">
                        {ad.gearShiftName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Fuel type" />
                      <Typography variant="subtitle1">
                        {ad.fuelTypeName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Body" />
                      <Typography variant="subtitle1">{ad.bodyName}</Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Km passed" />
                      <Typography variant="subtitle1">{ad.kmPassed}</Typography>
                    </ListItem>

                    <Divider />
                  </List>
                </>
              )}
            </Paper>
          </Grid>
          <Grid item xs={6}>
            <Paper style={{ padding: 50 }}>
              <Typography
                variant="h4"
                component="h2"
                style={{ marginBottom: 20, marginLeft: 13 }}
              >
                Additional description
              </Typography>
              {ad && (
                <>
                  <List disablePadding>
                    <ListItem>
                      <ListItemText primary="Renting location" />
                      <Typography variant="subtitle1">
                        {ad.cityName}, {ad.rentingStreetLocation}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Km restriction" />
                      <Typography variant="subtitle1">
                        {ad.kmRestriction}
                      </Typography>
                    </ListItem>
                    <Divider />
                    <ListItem>
                      <ListItemText primary="Price per day" />
                      <Typography variant="subtitle1">
                        {ad.priceFrom}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Number of children seats" />
                      <Typography variant="subtitle1">
                        {ad.numberChildSeats}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Collision damage waiver?" />
                      <Typography variant="subtitle1">
                        {ad.collisionDamage ? "YES" : "NO"}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Agent/User email" />
                      <Typography variant="subtitle1">
                        {ad.userEmail}
                      </Typography>
                    </ListItem>

                    <Divider />
                  </List>
                </>
              )}
            </Paper>
          </Grid>
          <Grid item sm={12}>
            <Grid container>
              <Grid
                item
                sm={12}
                md={6}
                style={{ display: "flex", flexDirection: "column" }}
              >
                <h2 style={{ marginBottom: 0 }}>
                  Comments for {ad?.userEmail} advertisements
                </h2>
                <Divider style={{ marginTop: 10, marginBottom: 15 }} />
                {comments.map((c) => (
                  <Paper
                    style={{ padding: 30, marginBottom: 15 }}
                    elevation={3}
                  >
                    <div
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                        color: "#a1a1a1",
                      }}
                    >
                      <p>Posted by: {c.username}</p>
                      <p>Created at: {c.creationDate}</p>
                    </div>
                    <p>{c.text}</p>
                  </Paper>
                ))}
              </Grid>
            </Grid>
          </Grid>
          <Backdrop className={classes.backdrop} open={loading}>
            <CircularProgress color="inherit" />
          </Backdrop>
        </Grid>
      </Dialog>
    </div>
  );
};

export default AdvertisementDetails;
