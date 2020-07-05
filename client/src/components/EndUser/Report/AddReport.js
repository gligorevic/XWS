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
import Alert from "@material-ui/lab/Alert";
import Slide from "@material-ui/core/Slide";
import FilledInput from "@material-ui/core/FilledInput";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import TextField from "@material-ui/core/TextField";

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
  margin: {
    margin: theme.spacing(1),
  },
}));

const ResponsiveDialog = ({ id, adId }) => {
  const [open, setOpen] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);
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

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseError = () => {
    setOpenError(false);
  };

  const [state, setState] = React.useState({
    km: "",
    text: "",
    requestId: id,
    adId: adId,
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(state);
    setLoading(true);
    const resp = await Axios.post("/price-list/report", state).catch(
      (error) => {
        if (error.response.status === 406) {
          setLoading(false);
          setOpen(false);
          setOpenError(true);
        }
      }
    );
    if (resp && resp.status >= 200 && resp.status < 300) {
      setLoading(false);
      setState({
        ...state,
        km: "",
        text: "",
      });
      setOpen(false);
      setOpenSuccess(true);
    }
  };
  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Add report
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Add report "}</DialogTitle>
        <DialogContent>
          <TextField
            id="standard-multiline-static"
            label="Km passed"
            type="number"
            fullWidth
            value={state.km}
            variant="outlined"
            name="km"
            onChange={handleChangeTextField}
          />
          <TextField
            id="standard-multiline-static"
            multiline
            fullWidth
            label="Text"
            style={{ marginTop: 10 }}
            rows={4}
            name="text"
            onChange={handleChangeTextField}
            value={state.text}
            variant="outlined"
          />
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
        open={openSuccess}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleCloseSuccess}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogContent>
          <Alert severity="success">Report has been added.</Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseSuccess} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
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
            Report for this request already exists.
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
