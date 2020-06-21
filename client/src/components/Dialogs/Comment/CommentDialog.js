import React, { useState, useEffect } from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Axios from "axios";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Typography from "@material-ui/core/Typography";
import CommentCard from "./CommentCard";
import CommentReply from "./CommentReply";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const CommentDialog = ({ request, open, setOpen, user }) => {
  const [comments, setComments] = useState([]);
  const classes = useStyles();

  const handleClose = () => {
    setOpen(-1);
  };

  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

  useEffect(() => {
    (async () => {
      const commentResp = await Axios.get(`/feedback/comment/${request.id}`);
      setComments(commentResp.data);
    })();
  }, []);

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseError = () => {
    setOpenFailure(false);
  };

  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Comments "}</DialogTitle>
        <DialogContent>
          {comments.length > 0 && (
            <List dense className={classes.root}>
              {comments &&
                comments.map((row) => {
                  return (
                    <ListItem key={row.id} button>
                      <CommentCard comment={row} user={user} />
                    </ListItem>
                  );
                })}
            </List>
          )}
          {comments.length == 0 &&
            user &&
            request &&
            user.role.some((r) => r.name === "ROLE_ENDUSER") &&
            user.username === request.userSentRequest && (
              <Typography
                variant="h6"
                component="h2"
                s
                style={{ marginBottom: 20 }}
              >
                <CommentReply
                  setOpenSuccess={setOpenSuccess}
                  setOpenFailure={setOpenFailure}
                  setErrorMessage={setErrorMessage}
                  requestId={request.id}
                  user={request.userSentRequest}
                  setOpen={setOpen}
                ></CommentReply>
              </Typography>
            )}

          {comments.length == 1 &&
            user &&
            user.role.some(
              (r) => r.name === "ROLE_ENDUSER" || r.name === "ROLE_AGENT"
            ) &&
            user.username === request.userEmail && (
              <Typography
                variant="h6"
                component="h2"
                s
                style={{ marginBottom: 20 }}
              >
                <CommentReply
                  setOpenSuccess={setOpenSuccess}
                  setOpenFailure={setOpenFailure}
                  setErrorMessage={setErrorMessage}
                  requestId={request.id}
                  user={request.userEmail}
                  setOpen={setOpen}
                ></CommentReply>
              </Typography>
            )}
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose} color="primary">
            Ok
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={openSuccess}
        autoHideDuration={2000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Comment sent successfully.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openFailure}
        autoHideDuration={2000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </div>
  );
};

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default connect(mapStateToProps, {})(CommentDialog);
