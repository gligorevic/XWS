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
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Typography from "@material-ui/core/Typography";
import CommentCard from "./CommentCard";
import CommentReply from "./CommentReply";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
}));

const CommentDialog = ({
  request,
  openCommentDialog,
  setOpenCommentDialog,
  comments,
  setComments,
  user,
}) => {
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const classes = useStyles();

  const handleClose = () => {
    setOpenCommentDialog(false);
  };

  return (
    <div>
      <Dialog
        open={openCommentDialog}
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
                  requestId={request.id}
                  user={request.userSentRequest}
                  setOpenCommentDialog={setOpenCommentDialog}
                ></CommentReply>
              </Typography>
            )}

          {comments.length == 1 &&
            user &&
            user.role.some((r) => r.name === "ROLE_AGENT") &&
            user.username === request.userEmail && (
              <Typography
                variant="h6"
                component="h2"
                s
                style={{ marginBottom: 20 }}
              >
                <CommentReply
                  requestId={request.id}
                  user={request.userEmail}
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
    </div>
  );
};

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default withRouter(connect(mapStateToProps, {})(CommentDialog));
