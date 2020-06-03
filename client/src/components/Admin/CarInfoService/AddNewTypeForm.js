import React, { useEffect } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";

import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import Axios from "axios";
import Grid from "@material-ui/core/Grid";

import { Button } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
      width: "25ch",
    },
  },
}));

const AddNewTypeForm = ({
  setAddNewType,
  addNewType,
  allBodyTypes,
  allFuelTypes,
  allGearShiftTypes,
  availableTypeList,
  setAvailableTypeList,
  selectedModel,
}) => {
  const classes = useStyles();
  const [selectedIndex, setSelectedIndex] = React.useState(null);
  const [selectedFuelType, setSelectedFuelType] = React.useState("");
  const [selectedGearType, setSelectedGearType] = React.useState("");
  const [selectedBodyType, setSelectedBodyType] = React.useState("");

  const [stateType, setStateType] = React.useState("");

  const handleSubmitType = async (e) => {
    try {
      e.preventDefault();
      console.log(addNewType);
      switch (addNewType) {
        case "fuel":
          const newfuelType = await Axios.post(
            `/car-info/fuel-type/${stateType.typeName}`
          );
          setAddNewType(null);
          return "";
        case "gear":
          const newGearType = await Axios.post(
            `/car-info/gear-shift-type/${stateType.typeName}`
          );
          setAddNewType(null);
          return "";
        case "body":
          const newBodyType = await Axios.post(
            `/car-info/body-type/${stateType.typeName}`
          );
          setAddNewType(null);
          return "";
        default:
          return "";
      }
    } catch (e) {
      console.log(e);
    }
  };

  const handleChooseSubmitType = async (e) => {
    try {
      e.preventDefault();
      switch (addNewType) {
        case "fuel":
          const newfuelType = await Axios.post(
            `/car-info/model/${selectedModel.modelName}/fuel-type/${selectedFuelType}`
          );
          setAddNewType(null);
          return "";
        case "gear":
          const newGearType = await Axios.post(
            `/car-info/model/${selectedModel.modelName}/gear-shift-type/${selectedGearType}`
          );
          setAddNewType(null);
          return "";
        case "body":
          const newBodyType = await Axios.post(
            `/car-info/model/${selectedModel.modelName}/body-type/${selectedBodyType}`
          );
          setAddNewType(null);
          return "";
        default:
          return "";
      }
    } catch (e) {
      console.log(e);
    }
  };

  const handleChangeType = (e) => {
    setStateType({ ...stateType, [e.target.name]: e.target.value });
    console.log(stateType);
  };

  return (
    <Paper>
      <h2 style={{ textAlign: "center" }}>Add new type</h2>
      {console.log(availableTypeList)}
      {console.log(addNewType)}
      <Grid container spacing={10}>
        {selectedModel !== null && (
          <>
            <Grid item xs={6}>
              <form
                className={classes.root}
                noValidate
                autoComplete="off"
                onSubmit={handleChooseSubmitType}
              >
                {addNewType === "fuel" && (
                  <Autocomplete
                    id="combo-box-fuel"
                    options={availableTypeList}
                    getOptionLabel={(option) => option.fuelTypeName}
                    style={{ width: 300 }}
                    renderInput={(params) => (
                      <TextField
                        {...params}
                        label="Fuel types"
                        variant="outlined"
                      />
                    )}
                    inputValue={selectedFuelType}
                    onInputChange={(event, newInputValue) => {
                      setSelectedFuelType(newInputValue);
                    }}
                  />
                )}
                {addNewType === "gear" && (
                  <Autocomplete
                    id="combo-box-gear"
                    options={availableTypeList}
                    getOptionLabel={(option) => option.gearShiftName}
                    style={{ width: 300 }}
                    renderInput={(params) => (
                      <TextField
                        {...params}
                        label="Gearshit types"
                        variant="outlined"
                      />
                    )}
                    inputValue={selectedGearType}
                    onInputChange={(event, newInputValue) => {
                      setSelectedGearType(newInputValue);
                    }}
                  />
                )}
                {addNewType === "body" && (
                  <Autocomplete
                    id="combo-box-body"
                    options={availableTypeList}
                    getOptionLabel={(option) => option.bodyTypeName}
                    style={{ width: 300 }}
                    renderInput={(params) => (
                      <TextField
                        {...params}
                        label="Body types"
                        variant="outlined"
                      />
                    )}
                    inputValue={selectedBodyType}
                    onInputChange={(event, newInputValue) => {
                      setSelectedBodyType(newInputValue);
                    }}
                  />
                )}

                <Button type="submit" variant="contained" color="primary">
                  Add existing type
                </Button>
              </form>
            </Grid>
          </>
        )}

        <Grid item xs={6}>
          <form
            className={classes.root}
            noValidate
            autoComplete="off"
            onSubmit={handleSubmitType}
          >
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="typeName"
              label="Type name"
              id="typeName"
              onChange={handleChangeType}
            />
            <br />
            <Button type="submit" variant="contained" color="primary">
              Add new type
            </Button>
          </form>
        </Grid>
      </Grid>
    </Paper>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.user.user,
});

export default connect(mapStateToProps)(AddNewTypeForm);
