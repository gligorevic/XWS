import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import useMediaQuery from "@material-ui/core/useMediaQuery";
import { useTheme } from "@material-ui/core/styles";

import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import { makeStyles } from "@material-ui/core/styles";
import Axios from "axios";
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import Alert from "@material-ui/lab/Alert";
import Slide from "@material-ui/core/Slide";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const ResponsiveDialog = ({ id }) => {
  const [open, setOpen] = React.useState(false);
  const [openError, setOpenError] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const classes = useStyles();
  const [loading, setLoading] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleCloseError = () => {
    setOpenError(false);
  };

  const [state, setState] = React.useState({
    startDate: new Date(),
    endDate: new Date(),
    advertisementId: id,
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(state);
    setLoading(true);
    const resp = await Axios.post(
      "/advertisement/reservationPeriod",
      state
    ).catch((error) => {
      if (error.response.status === 406) {
        setLoading(false);
        setOpenError(true);
      }
    });
    setLoading(false);
    setOpen(false);
  };

  const handleDateChangeFrom = (date) => {
    setState({
      ...state,
      startDate: date,
    });
  };

  const handleDateChangeTo = (date) => {
    setState({
      ...state,
      endDate: date,
    });
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Add reservation period
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {"Add reservation period for selected advertisement "}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>Select start date and end date.</DialogContentText>
          <MuiPickersUtilsProvider utils={DateFnsUtils} id="fromDate">
            <KeyboardDatePicker
              id="date-picker-dialog"
              label="From: "
              format="dd/MM/yyyy"
              KeyboardButtonProps={{
                "aria-label": "change date",
              }}
              className={classes.datePicker}
              minDate={new Date()}
              value={state.startDate}
              onChange={handleDateChangeFrom}
            />
          </MuiPickersUtilsProvider>
          <MuiPickersUtilsProvider utils={DateFnsUtils} id="fromDate">
            <KeyboardDatePicker
              id="date-picker-dialog2"
              className={classes.datePicker}
              label="To: "
              format="dd/MM/yyyy"
              KeyboardButtonProps={{
                "aria-label": "change date",
              }}
              minDate={state.startDate}
              value={state.endDate}
              onChange={handleDateChangeTo}
            />
          </MuiPickersUtilsProvider>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary" autoFocus>
            Add
          </Button>
        </DialogActions>
      </Dialog>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Dialog
        open={openError}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleCloseError}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="error">
            Advertisement is already reserved in selected period.
          </Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseError} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
const mapStateToProps = (state) => ({});

export default withRouter(connect(mapStateToProps, {})(ResponsiveDialog));
