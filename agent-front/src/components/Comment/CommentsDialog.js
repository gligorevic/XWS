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
import {
  getAdvertisementComments,
  setAdvertisementComments,
} from "../../store/actions/comment";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { format } from "date-fns";
import Typography from "@material-ui/core/Typography";
import CommentCard from "./CommentCard";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const ResponsiveDialog = ({
  id,
  comments,
  getAdvertisementComments,
  setAdvertisementComments,
}) => {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const classes = useStyles();

  const handleClickOpen = () => {
    getAdvertisementComments(id);
    setOpen(true);
  };

  const handleClose = () => {
    setAdvertisementComments([]);
    setOpen(false);
  };

  return (
    <div>
      <Button size="small" color="primary" onClick={handleClickOpen}>
        Comments
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Comments "}</DialogTitle>
        <DialogContent>
          {comments && (
            <List dense className={classes.root}>
              {comments &&
                comments.map((row) => {
                  return (
                    <ListItem key={row.id} button>
                      <CommentCard comment={row} setOpen={setOpen} />
                    </ListItem>
                  );
                })}
            </List>
          )}
          {comments.length == 0 && (
            <Typography
              variant="h6"
              component="h2"
              s
              style={{ marginBottom: 20 }}
            >
              There aren't any comments for this advertisement.
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
  comments: state.comment.advertisementComments,
});

export default withRouter(
  connect(mapStateToProps, {
    getAdvertisementComments,
    setAdvertisementComments,
  })(ResponsiveDialog)
);
