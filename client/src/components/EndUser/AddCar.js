import React from "react";
import { withRouter } from "react-router-dom";
import Grid from "@material-ui/core/Grid";
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
}));
const AddCar = ({ username }) => {
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

  const [loading, setLoading] = React.useState(false);
  const [open, setOpen] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);
  const handleClose = () => {
    setOpen(false);
  };
  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    console.log(state);
    const resp = await Axios.post("/car", state).catch((error) => {
      if (error.response && error.response.status === 403) {
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
            type="number"
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
