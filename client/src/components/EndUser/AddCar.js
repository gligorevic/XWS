import React, { useEffect } from "react";
import { withRouter } from "react-router-dom";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import { makeStyles } from "@material-ui/core/styles";
import Axios from "axios";
import Button from "@material-ui/core/Button";
import { connect } from "react-redux";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Alert from "@material-ui/lab/Alert";
import Slide from "@material-ui/core/Slide";
import Typography from "@material-ui/core/Typography";
import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import ListItemText from "@material-ui/core/ListItemText";

import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import InfoIcon from "@material-ui/icons/Info";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Divider from "@material-ui/core/Divider";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

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
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
}));

function getSteps() {
  return [
    "Brand selection",
    "Model selection",
    "Car info selection",
    "Add Km passed",
    "Confirmation",
  ];
}

function getStepContent(
  step,
  state,
  handleListItemClickBrand,
  allBrands,
  selectedBrand,
  allModels,
  selectedModel,
  handleListItemClickModel,
  value,
  handleChangeValue,
  allBodyTypes,
  allFuelTypes,
  allGearShiftTypes,
  selectedBodyType,
  selectedFuelType,
  selectedGearShiftType,
  setSelectedBodyType,
  setSelectedFuelType,
  setSelectedGearShiftType,
  handleChangeTextField
) {
  const today = new Date();
  switch (step) {
    case 0:
      return (
        <>
          <List
            component="nav"
            aria-label="main mailbox folders"
            style={{ height: 400, overflowY: "scroll" }}
          >
            {allBrands.map((brand) => {
              return (
                <ListItem
                  button
                  selected={selectedBrand && brand.id === selectedBrand.id}
                  onClick={(event) => handleListItemClickBrand(event, brand)}
                >
                  <ListItemText primary={brand.brandName} />
                </ListItem>
              );
            })}
          </List>
        </>
      );
    case 1:
      return (
        <>
          {selectedBrand && allModels.length > 0 ? (
            <List
              component="nav"
              aria-label="main mailbox folders"
              style={{ height: 400, overflowY: "scroll" }}
            >
              {allModels.map((model) => {
                return (
                  <ListItem
                    button
                    selected={selectedModel && selectedModel.id === model.id}
                    onClick={(event) => handleListItemClickModel(event, model)}
                  >
                    <ListItemText primary={model.modelName} />
                  </ListItem>
                );
              })}
            </List>
          ) : (
            <div
              style={{
                minHeight: 400,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                color: "#777",
              }}
            >
              <InfoIcon />
              {allModels.length === 0
                ? "Selected brand has not models yet."
                : "Select one brand"}
            </div>
          )}
        </>
      );
    case 2:
      return (
        <>
          <Tabs
            value={value}
            onChange={handleChangeValue}
            indicatorColor="primary"
            textColor="primary"
            centered
          >
            <Tab label="Fuel type" />
            <Tab label="Gearshift type" />
            <Tab label="Body type" />
          </Tabs>
          {value === 0 && (
            <>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{ height: 352, overflowY: "scroll" }}
              >
                {allFuelTypes.map((fuelType) => {
                  return (
                    <ListItem
                      button
                      selected={
                        selectedFuelType && selectedFuelType.id === fuelType.id
                      }
                      onClick={() => {
                        setSelectedFuelType(fuelType);
                      }}
                    >
                      <ListItemText primary={fuelType.fuelTypeName} />
                    </ListItem>
                  );
                })}
              </List>
            </>
          )}
          {value === 1 && (
            <>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{ height: 352, overflowY: "scroll" }}
              >
                {allGearShiftTypes.map((gearShiftType) => {
                  return (
                    <ListItem
                      button
                      selected={
                        selectedGearShiftType &&
                        selectedGearShiftType.id === gearShiftType.id
                      }
                      onClick={() => {
                        setSelectedGearShiftType(gearShiftType);
                      }}
                    >
                      <ListItemText primary={gearShiftType.gearShiftName} />
                    </ListItem>
                  );
                })}
              </List>
            </>
          )}
          {value === 2 && (
            <>
              <List
                component="nav"
                aria-label="main mailbox folders"
                style={{ height: 352, overflowY: "scroll" }}
              >
                {allBodyTypes.map((bodyType) => {
                  return (
                    <ListItem
                      button
                      selected={
                        selectedBodyType && selectedBodyType.id === bodyType.id
                      }
                      onClick={() => {
                        setSelectedBodyType(bodyType);
                      }}
                    >
                      <ListItemText primary={bodyType.bodyTypeName} />
                    </ListItem>
                  );
                })}
              </List>
            </>
          )}
        </>
      );
    case 3:
      return (
        <>
          <TextField
            fullWidth
            type="number"
            name="kmPassed"
            onChange={handleChangeTextField}
            value={state.kmPassed}
            required
            label="Km passed"
          />
        </>
      );
    case 4:
      return (
        <Paper style={{ padding: 50, paddingBottom: 75 }}>
          <Typography
            variant="h4"
            component="h2"
            style={{ marginBottom: 20, marginLeft: 13 }}
          >
            Check your data entry
          </Typography>
          <List disablePadding>
            <ListItem>
              <ListItemText primary="Brand" />
              <Typography variant="subtitle1">
                {selectedBrand.brandName}
              </Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Model" />
              <Typography variant="subtitle1">
                {selectedModel.modelName}
              </Typography>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemText primary="Gear shift" />
              <Typography variant="subtitle1">
                {selectedGearShiftType.gearShiftName}
              </Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Fuel type" />
              <Typography variant="subtitle1">
                {selectedFuelType.fuelTypeName}
              </Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Body" />
              <Typography variant="subtitle1">
                {selectedBodyType.bodyTypeName}
              </Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Km passed" />
              <Typography variant="subtitle1">{state.kmPassed}</Typography>
            </ListItem>

            <Divider />
          </List>
        </Paper>
      );
    default:
      return "Unknown step";
  }
}

const AddCar = ({ username }) => {
  useEffect(() => {
    (async () => {
      const res = await Axios.get("/car-info/brand");
      setAllBrands(res.data);
    })();
  }, []);
  const classes = useStyles();
  const [state, setState] = React.useState({
    brandName: "",
    modelName: "",
    gearShiftName: "",
    fuelTypeName: "",
    bodyName: "",
    kmPassed: "",
    userEmail: username,
  });

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const [value, setValue] = React.useState(0);

  const handleChangeValue = (event, newValue) => {
    setValue(newValue);
  };

  const [loading, setLoading] = React.useState(false);
  const [open, setOpen] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [selectedBrand, setSelectedBrand] = React.useState(null);
  const [selectedModel, setSelectedModel] = React.useState(null);
  const [selectedFuelType, setSelectedFuelType] = React.useState(null);
  const [selectedBodyType, setSelectedBodyType] = React.useState(null);
  const [selectedGearShiftType, setSelectedGearShiftType] = React.useState(
    null
  );
  const [allModels, setAllModels] = React.useState([]);
  const [allBrands, setAllBrands] = React.useState([]);
  const [allFuelTypes, setAllFuelTypes] = React.useState([]);
  const [allGearShiftTypes, setAllGearShiftTypes] = React.useState([]);
  const [allBodyTypes, setAllBodyTypes] = React.useState([]);
  const handleClose = () => {
    setOpen(false);
  };

  const handleListItemClickBrand = async (event, brand) => {
    if (selectedBrand && brand.id === selectedBrand.id) setSelectedBrand(null);
    else {
      const res = await Axios.get(`/car-info/brand/${brand.brandName}/model`);
      setAllModels(res.data);

      brand.models = res.data;
      setSelectedBrand(brand);
    }
  };

  const handleListItemClickModel = async (event, model) => {
    if (selectedModel && selectedModel.id === model.id) setSelectedModel(null);
    else {
      setSelectedModel(model);
      const fuelType = await Axios.get(`/car-info/model/${model.id}/fuel-type`);
      const gearShiftType = await Axios.get(
        `/car-info/model/${model.id}/gear-shift-type`
      );
      const bodyType = await Axios.get(`/car-info/model/${model.id}/body-type`);

      setAllBodyTypes(bodyType.data);
      setAllFuelTypes(fuelType.data);
      setAllGearShiftTypes(gearShiftType.data);
    }
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    state.modelName = selectedModel.modelName;
    state.brandName = selectedBrand.brandName;
    state.fuelTypeName = selectedFuelType.fuelTypeName;
    state.gearShiftName = selectedGearShiftType.gearShiftName;
    state.bodyName = selectedBodyType.bodyTypeName;
    console.log(state);
    const resp = await Axios.post("/car", state).catch((error) => {
      if (error.response.status === 403) {
        setLoading(false);
        setOpen(true);
      }
    });
    setLoading(false);

    if (resp != undefined && resp.status === 200) {
      setState({
        brandName: "",
        modelName: "",
        gearShiftName: "",
        fuelTypeName: "",
        bodyName: "",
        kmPassed: "",
      });
      setOpenSuccess(true);
    }
  };
  const steps = getSteps();
  const [activeStep, setActiveStep] = React.useState(0);

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };
  const flag = true;

  return (
    <div>
      <div>
        <div
          style={{
            marginTop: 64,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h5">
            Add a new car
          </Typography>
        </div>
        <Stepper activeStep={activeStep}>
          {steps.map((label, index) => {
            return (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            );
          })}
        </Stepper>
        <form className={classes.form} noValidate onSubmit={handleSubmit}>
          <div className={classes.instructions}>
            {getStepContent(
              activeStep,
              state,
              handleListItemClickBrand,
              allBrands,
              selectedBrand,
              allModels,
              selectedModel,
              handleListItemClickModel,
              value,
              handleChangeValue,
              allBodyTypes,
              allFuelTypes,
              allGearShiftTypes,
              selectedBodyType,
              selectedFuelType,
              selectedGearShiftType,
              setSelectedBodyType,
              setSelectedFuelType,
              setSelectedGearShiftType,
              handleChangeTextField
            )}
          </div>
          <Button
            disabled={activeStep === 0}
            onClick={handleBack}
            className={classes.button}
            style={{ marginTop: 10 }}
          >
            Back
          </Button>

          {activeStep !== steps.length - 1 ? (
            <Button
              style={{ marginTop: 10 }}
              variant="contained"
              color="primary"
              onClick={handleNext}
              className={classes.button}
            >
              Next
            </Button>
          ) : (
            <Button
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={handleSubmit}
            >
              Save
            </Button>
          )}
        </form>{" "}
      </div>

      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="error">
            You are not allowed to enter more than 3 cars.
          </Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Ok
          </Button>
        </DialogActions>
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
            You have successfully added a new car.
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

const mapStateToProps = (state) => ({
  username: state.user.user.username,
});

export default withRouter(connect(mapStateToProps)(AddCar));
