import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import TextField from "@material-ui/core/TextField";
import Axios from "axios";
import Autocomplete from "@material-ui/lab/Autocomplete";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";

export default function AddNewTypeDialog({
  availableTypes,
  open,
  handleClose,
  currentDialog,
  setAddDialogOpened,
  selectedModel,
  setAllFuelTypes,
  setAllGearShiftTypes,
  setAllBodyTypes,
  isModelSelected,
}) {
  const [stateType, setStateType] = React.useState("");

  const handleSubmitType = async (e) => {
    try {
      switch (currentDialog) {
        case 2:
          const newfuelType = await Axios.post(
            `/car-info/fuel-type`,
            stateType,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          break;
        case 3:
          const newGearType = await Axios.post(
            `/car-info/gear-shift-type`,
            stateType,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          break;
        case 4:
          console.log(stateType.toString());
          const newBodyType = await Axios.post(
            `/car-info/body-type`,
            stateType.toString(),
            {
              headers: { "Content-Type": "text/plain" },
            }
          );

          break;
        default:
          break;
      }
      setAddDialogOpened(-1);
    } catch (e) {
      console.log(e);
    }
  };

  const handleChooseSubmitType = async (e) => {
    try {
      e.preventDefault();
      const forStateUpdate = availableTypes.find(
        (t) => t[typeName] === selectedType
      );
      switch (currentDialog) {
        case 2:
          const newfuelType = await Axios.post(
            `/car-info/model/${selectedModel.id}/fuel-type`,
            selectedType,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          setAllFuelTypes((oldState) => {
            console.log(oldState);
            return [...oldState, forStateUpdate];
          });
          break;
        case 3:
          const newGearType = await Axios.post(
            `/car-info/model/${selectedModel.id}/gear-shift-type`,
            selectedType,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          setAllGearShiftTypes((oldState) => [...oldState, forStateUpdate]);
          break;
        case 4:
          const newBodyType = await Axios.post(
            `/car-info/model/${selectedModel.id}/body-type`,
            selectedType,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );

          setAllBodyTypes((oldState) => [...oldState, forStateUpdate]);
          break;
        default:
          break;
      }
      setAddDialogOpened(-1);
    } catch (e) {
      console.log(e);
    }
  };
  const handleChangeType = (e) => {
    setStateType(e.target.value);
  };

  const [selectedType, setSelectedType] = React.useState("");

  let typeName;
  switch (currentDialog) {
    case 2:
      typeName = "fuelTypeName";
      break;
    case 3:
      typeName = "gearShiftName";
      break;
    case 4:
      typeName = "bodyTypeName";
  }

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
      maxWidth="md"
      fullWidth
    >
      <DialogContent>
        <Grid container spacing={2}>
          <Grid
            item
            sm={5}
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              flexDirection: "column",
            }}
          >
            <h2>Bound new type with model</h2>
            <Autocomplete
              id="combo-box-fuel"
              options={availableTypes}
              getOptionLabel={(option) => option[typeName]}
              style={{ width: 300 }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label={
                    currentDialog === 2
                      ? "Fuel types"
                      : currentDialog === 3
                      ? "Gearshit types"
                      : "Body types"
                  }
                  variant="outlined"
                />
              )}
              inputValue={selectedType}
              onInputChange={(e, newInputValue) => {
                setSelectedType(newInputValue);
              }}
            />
            <Button
              variant="contained"
              color="primary"
              onClick={handleChooseSubmitType}
              disabled={!isModelSelected}
            >
              Add existing type
            </Button>
          </Grid>
          <Grid
            item
            sm={2}
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Divider orientation="vertical" />
          </Grid>
          <Grid
            item
            sm={5}
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              flexDirection: "column",
            }}
          >
            <h2>Add new type</h2>
            <TextField
              variant="outlined"
              margin="normal"
              required
              value={stateType}
              fullWidth
              placeholder="Enter new type name"
              onChange={handleChangeType}
              style={{ width: 300 }}
            />

            <Button
              variant="contained"
              color="primary"
              onClick={handleSubmitType}
            >
              Add new type
            </Button>
          </Grid>
        </Grid>
      </DialogContent>
    </Dialog>
  );
}
