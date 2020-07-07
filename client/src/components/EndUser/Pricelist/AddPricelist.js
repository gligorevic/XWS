import React, { useState } from "react";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";
import { connect } from "react-redux";
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import { getPricelists } from "../../../store/actions/pricelist";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const AddPricelist = ({ open, setOpen, getPricelists }) => {
  const [success, setSuccess] = useState();
  const classes = useStyles();
  const [loading, setLoading] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

  const [state, setState] = React.useState({
    validFrom: new Date(),
    validTo: new Date(),
  });

  const handleDateChangeFrom = (date) => {
    setState({
      ...state,
      validFrom: date,
    });
  };

  const handleDateChangeTo = (date) => {
    setState({
      ...state,
      validTo: date,
    });
  };

  const handleCloseError = () => {
    setOpenFailure(false);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(state);
    setLoading(true);
    const resp = await Axios.post("/price-list", state).catch((error) => {
      if (error.response.status === 406) {
        setLoading(false);
        setErrorMessage("Dates of price lists are overlapping!");
        setOpenFailure(true);
      } else {
        setErrorMessage("Something went wrong.");
        setOpenFailure(true);
      }
    });
    if (resp && resp.status >= 200 && resp.status < 300) {
      getPricelists();
    }

    setLoading(false);
    setOpen(false);
  };

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle id="responsive-dialog-title">
          {"Add new price list "}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>Select validity period.</DialogContentText>
          <MuiPickersUtilsProvider utils={DateFnsUtils} id="fromDate">
            <KeyboardDatePicker
              id="date-picker-dialog"
              label="Valid from: "
              format="dd/MM/yyyy"
              KeyboardButtonProps={{
                "aria-label": "change date",
              }}
              className={classes.datePicker}
              minDate={new Date()}
              value={state.validFrom}
              onChange={handleDateChangeFrom}
            />
          </MuiPickersUtilsProvider>
          <MuiPickersUtilsProvider utils={DateFnsUtils} id="toDate">
            <KeyboardDatePicker
              id="date-picker-dialog2"
              className={classes.datePicker}
              label="Valid to: "
              format="dd/MM/yyyy"
              KeyboardButtonProps={{
                "aria-label": "change date",
              }}
              minDate={state.validFrom}
              value={state.validTo}
              onChange={handleDateChangeTo}
            />
          </MuiPickersUtilsProvider>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={() => setOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary" autoFocus>
            Add
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={!open && success}
        autoHideDuration={4500}
        onClose={() => setSuccess(false)}
      >
        <Alert onClose={() => setSuccess(false)} severity="success">
          Successfully added new pricelist!
        </Alert>
      </Snackbar>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Snackbar
        open={openFailure}
        autoHideDuration={2000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </>
  );
};

const mapStateToProps = (state) => ({
  userEmail: state.user.user.username,
});

export default connect(mapStateToProps, { getPricelists })(AddPricelist);
