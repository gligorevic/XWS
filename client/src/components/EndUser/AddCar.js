import React from "react";
import { withRouter } from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import { makeStyles } from "@material-ui/core/styles";
import Axios from "axios";
import Button from "@material-ui/core/Button";
const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 270,
    maxWidth: 300,
  },
  root: {
    display: "flex",
    flexDirection: "column",
  },
  textCenter: {
    textAlign: "center",
  },
}));
const AddCar = ({}) => {
  const classes = useStyles();
  const [state, setState] = React.useState({
    brandName: "",
    modelName: "",
    gearShiftName: "",
    fuelTypeName: "",
    bodyName: "",
    kmPassed: "",
    isAgent: false,
  });

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const resp = await Axios.post("/car", state);
    if (resp.status === 200) {
      setState({
        brandName: "",
        modelName: "",
        gearShiftName: "",
        fuelTypeName: "",
        bodyName: "",
        kmPassed: "",
      });
    }
  };

  return (
    <div>
      <Grid item xs={8}>
        <Paper className={classes.root} style={{ padding: 20 }}>
          <TextField
            name="brandName"
            onChange={handleChangeTextField}
            required
            value={state.brandName}
            label="brand"
          />
          <TextField
            name="modelName"
            onChange={handleChangeTextField}
            required
            value={state.modelName}
            label="model"
          />
          <TextField
            name="gearShiftName"
            onChange={handleChangeTextField}
            value={state.gearShiftName}
            required
            label="gear shift"
          />
          <TextField
            name="fuelTypeName"
            onChange={handleChangeTextField}
            value={state.fuelTypeName}
            required
            label="fuel Type"
          />
          <TextField
            name="bodyName"
            onChange={handleChangeTextField}
            value={state.bodyName}
            required
            label="bodyName"
          />
          <TextField
            name="kmPassed"
            onChange={handleChangeTextField}
            value={state.kmPassed}
            required
            label="kmPassed"
          />
        </Paper>
        <Button onClick={handleSubmit} color="primary">
          Add
        </Button>
      </Grid>
    </div>
  );
};

export default withRouter(AddCar);
