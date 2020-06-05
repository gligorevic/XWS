import React, { useEffect, useState } from "react";
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
import TextField from "@material-ui/core/TextField";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Axios from "axios";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Slide from "@material-ui/core/Slide";

import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Alert from "@material-ui/lab/Alert";
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";

import CitySearch from "../Search/CitySearch";

const useStyles = makeStyles((theme) => ({
  text: {},
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  appBar: {
    position: "relative",
  },
  title: {
    marginLeft: theme.spacing(2),
    flex: 1,
  },
  datePicker: {
    padding: 10,
  },
}));

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const AddAdvertisement = ({ carId, open, setOpen }) => {
  const [cityName, setCityName] = useState("");
  const [error, setError] = useState(false);

  const [state, setState] = React.useState({
    carId: "",
    brandName: "",
    modelName: "",
    gearShiftName: "",
    fuelTypeName: "",
    bodyName: "",
    kmPassed: "",
    userEmail: "",
    kmRestriction: "",
    price: "",
    numberChildSeats: "",
    collisionDamage: false,
    rentingLocation: "",
    freeFrom: new Date(),
    freeTo: new Date(),
  });

  const classes = useStyles();

  useEffect(() => {
    (async () => {
      const res = await Axios.get(`/car/${carId}`);
      const car = res.data;
      setState({
        carId: car.id,
        brandName: car.brandName,
        modelName: car.modelName,
        gearShiftName: car.gearShiftName,
        fuelTypeName: car.fuelTypeName,
        bodyName: car.bodyName,
        kmPassed: car.kmPassed,
        userEmail: car.userEmail,
        kmRestriction: "",
        price: "",
        numberChildSeats: "",
        collisionDamage: false,
        rentingStreetLocation: "",
        freeFrom: new Date(),
        freeTo: new Date(),
      });
    })();
  }, [open]);

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event) => {
    setState({
      ...state,
      collisionDamage: event.target.checked,
    });
  };

  const handleDateChangeFrom = (date) => {
    setState({
      ...state,
      freeFrom: date,
    });
  };

  const handleDateChangeTo = (date) => {
    setState({
      ...state,
      freeTo: date,
    });
  };

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };
  const [loading, setLoading] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
    setOpen(false);
  };

  const handleSubmit = async (e) => {
    console.log(state);
    setLoading(true);
    const resp = await Axios.post("/search", { ...state, cityName });
    setLoading(false);
    if (resp.status === 200) {
      console.log("Uspesno");
      setOpen(false);
      setOpenSuccess(true);
    }
  };

  return (
    <div>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
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
              Add advertisement
            </Typography>
          </Toolbar>
        </AppBar>
        <Grid container spacing={2} style={{ padding: 60, paddingTop: 120 }}>
          <Grid item xs={6}>
            <Paper style={{ padding: 50 }}>
              <Typography
                variant="h4"
                component="h2"
                style={{ marginBottom: 20, marginLeft: 13 }}
              >
                Car
              </Typography>
              {state && (
                <>
                  <List disablePadding>
                    <ListItem>
                      <ListItemText primary="Brand" />
                      <Typography variant="subtitle1">
                        {state.brandName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Model" />
                      <Typography variant="subtitle1">
                        {state.modelName}
                      </Typography>
                    </ListItem>
                    <Divider />
                    <ListItem>
                      <ListItemText primary="Gear shift" />
                      <Typography variant="subtitle1">
                        {state.gearShiftName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Fuel type" />
                      <Typography variant="subtitle1">
                        {state.fuelTypeName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Body" />
                      <Typography variant="subtitle1">
                        {state.bodyName}
                      </Typography>
                    </ListItem>
                    <ListItem>
                      <ListItemText primary="Km passed" />
                      <Typography variant="subtitle1">
                        {state.kmPassed}
                      </Typography>
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
                Enter requiered data
              </Typography>
              <TextField
                className={classes.text}
                fullWidth
                type="number"
                name="kmRestriction"
                onChange={handleChangeTextField}
                value={state.kmRestriction}
                required
                label="Km restriction"
              />
              <TextField
                className={classes.text}
                fullWidth
                name="price"
                type="number"
                onChange={handleChangeTextField}
                value={state.price}
                required
                label="Price"
              />
              <TextField
                className={classes.text}
                fullWidth
                type="number"
                name="numberChildSeats"
                onChange={handleChangeTextField}
                value={state.numberChildSeats}
                required
                label="Number of children seats"
              />
              <TextField
                className={classes.text}
                fullWidth
                name="rentingLocation"
                onChange={handleChangeTextField}
                value={state.rentingLocation}
                required
                label="Renting location"
              />
              <MuiPickersUtilsProvider utils={DateFnsUtils} id="fromDate">
                <KeyboardDatePicker
                  id="date-picker-dialog"
                  label="Free from: "
                  format="dd/MM/yyyy"
                  KeyboardButtonProps={{
                    "aria-label": "change date",
                  }}
                  className={classes.datePicker}
                  minDate={new Date()}
                  value={state.freeFrom}
                  onChange={handleDateChangeFrom}
                />
              </MuiPickersUtilsProvider>
              <MuiPickersUtilsProvider utils={DateFnsUtils} id="toDate">
                <KeyboardDatePicker
                  id="date-picker-dialog2"
                  className={classes.datePicker}
                  label="Free to: "
                  format="dd/MM/yyyy"
                  KeyboardButtonProps={{
                    "aria-label": "change date",
                  }}
                  minDate={state.freeFrom}
                  value={state.freeTo}
                  onChange={handleDateChangeTo}
                />
              </MuiPickersUtilsProvider>
              <FormControlLabel
                className={classes.text}
                control={
                  <Checkbox
                    fullWidth
                    checked={state.collisionDamage}
                    onChange={handleChange}
                    name="collisionDamage"
                    color="primary"
                  />
                }
                label="Collision Damage Wavier"
              />
              <CitySearch
                cityName={cityName}
                setCityName={setCityName}
                error={error}
                setError={setError}
              />
              <Button onClick={handleSubmit} color="primary">
                Add
              </Button>
            </Paper>
          </Grid>
          <Backdrop className={classes.backdrop} open={loading}>
            <CircularProgress color="inherit" />
          </Backdrop>
        </Grid>
      </Dialog>
      <Dialog
        open={openSuccess}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleCloseSuccess}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="success">
            You have successfully added a new advertisement.
          </Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseSuccess} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

function mapStateToProps(state) {
  return {};
}

export default connect(mapStateToProps, {})(AddAdvertisement);
