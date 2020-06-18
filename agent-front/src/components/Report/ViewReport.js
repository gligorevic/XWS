import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import useMediaQuery from "@material-ui/core/useMediaQuery";
import { useTheme } from "@material-ui/core/styles";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";
import { getReport } from "../../store/actions/report";
import Typography from "@material-ui/core/Typography";

import TextField from "@material-ui/core/TextField";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const ResponsiveDialog = ({ id, report, getReport }) => {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const classes = useStyles();

  const handleClickOpen = () => {
    getReport(id);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        View report
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Report"}</DialogTitle>
        <DialogContent>
          {report && (
            <>
              <TextField
                id="standard-multiline-static"
                label="Km"
                multiline
                rows={1}
                fullWidth
                value={report.km}
                variant="outlined"
              />
              <TextField
                id="standard-multiline-static"
                multiline
                fullWidth
                style={{ marginTop: 10 }}
                rows={4}
                value={report.text}
                variant="outlined"
              />
            </>
          )}
          {!report && (
            <Typography
              variant="h6"
              component="h2"
              style={{ marginBottom: 20 }}
            >
              Report doesn't exist.
            </Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
const mapStateToProps = (state) => ({
  report: state.report.report,
});

export default withRouter(
  connect(mapStateToProps, { getReport })(ResponsiveDialog)
);
