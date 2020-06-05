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
import { getReservationPeriods } from "../../store/actions/advertisement";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { format } from "date-fns";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const ResponsiveDialog = ({ id, periods, getReservationPeriods }) => {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const classes = useStyles();

  const handleClickOpen = () => {
    getReservationPeriods(id);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        View reserved periods
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {"List of reservation periods for selected advertisement "}
        </DialogTitle>
        <DialogContent>
          {periods && (
            <List dense className={classes.root}>
              {periods &&
                periods.map((row) => {
                  return (
                    <ListItem key={row.id} button>
                      <ListItemText
                        disableTypography
                        style={{ fontSize: 20 }}
                        primary={
                          format(new Date(row.startDate), "dd MMM yyyy") +
                          "  -  " +
                          format(new Date(row.endDate), "dd MMM yyyy")
                        }
                      />
                    </ListItem>
                  );
                })}
            </List>
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
  periods: state.advertisement.reservationPeriods,
});

export default withRouter(
  connect(mapStateToProps, { getReservationPeriods })(ResponsiveDialog)
);
