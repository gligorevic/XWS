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
import TextField from "@material-ui/core/TextField";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Axios from "axios";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  text: {
    marginBottom: 10,
    marginTop: 10,
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
}));

const AddAdvertisement = ({ car }) => {
  const classes = useStyles();

  const [state, setState] = React.useState({
    carId: car.id,
    brandName: car.brandName,
    modelName: car.modelName,
    gearShiftName: car.gearShiftName,
    fuelTypeName: car.fuelTypeName,
    bodyName: car.bodyName,
    kmPassed: car.kmPassed,
    userAgentId: car.userAgentId,
    kmRestriction: "",
    price: "",
    numberChildSeats: "",
    collisionDamage: false,
  });

  const handleChange = (event) => {
    setState({
      ...state,
      collisionDamage: event.target.checked,
    });
  };

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };
  const [loading, setLoading] = React.useState(false);

  const handleSubmit = async (e) => {
    console.log(state);
    setLoading(true);
    const resp = await Axios.post("/advertisement", state);
    setLoading(false);
    if (resp.status === 200) {
      console.log("Uspesno");
    }
  };

  return (
    <Grid container className={classes.root} spacing={2}>
      <Grid item xs={6}>
        <Paper style={{ padding: 50, paddingBottom: 75 }}>
          <Typography
            variant="h4"
            component="h2"
            style={{ marginBottom: 20, marginLeft: 13 }}
          >
            Car
          </Typography>
          {car && (
            <>
              <List disablePadding>
                <ListItem>
                  <ListItemText primary="Brand" />
                  <Typography variant="subtitle1">{car.brandName}</Typography>
                </ListItem>
                <ListItem>
                  <ListItemText primary="Model" />
                  <Typography variant="subtitle1">{car.modelName}</Typography>
                </ListItem>
                <Divider />
                <ListItem>
                  <ListItemText primary="Gear shift" />
                  <Typography variant="subtitle1">
                    {car.gearShiftName}
                  </Typography>
                </ListItem>
                <ListItem>
                  <ListItemText primary="Fuel type" />
                  <Typography variant="subtitle1">
                    {car.fuelTypeName}
                  </Typography>
                </ListItem>
                <ListItem>
                  <ListItemText primary="Body" />
                  <Typography variant="subtitle1">{car.bodyName}</Typography>
                </ListItem>
                <ListItem>
                  <ListItemText primary="Km passed" />
                  <Typography variant="subtitle1">{car.kmPassed}</Typography>
                </ListItem>

                <Divider />
              </List>
            </>
          )}
        </Paper>
      </Grid>
      <Grid item xs={6}>
        <Paper style={{ padding: 50, paddingBottom: 75 }}>
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
          <Button onClick={handleSubmit} color="primary">
            Add
          </Button>
        </Paper>
      </Grid>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </Grid>
  );
};

function mapStateToProps(state) {
  return {
    car: state.cars.carForAdvertisement,
  };
}

export default connect(mapStateToProps, {})(AddAdvertisement);
