import React, { useEffect, useState } from "react";
import { useTheme } from "@material-ui/core/styles";

import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";
import axios from "axios";
import ListReservedRequests from "./ListReservedRequests";
import MessageBox from "./MessageBox";
import clsx from "clsx";

const useStyles = makeStyles((theme) => ({
  root: {
    height: 420,
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
  datePicker: {
    padding: 10,
  },
  paper: {
    padding: theme.spacing(1),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
  fixedHeight: {
    height: "100%",
  },
}));

const MessageTab = ({ id, user }) => {
  const [messages, setMessages] = useState([]);
  const [selectedRequestId, setSelecetedRequestId] = useState(-1);

  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

  const theme = useTheme();

  return (
    <>
      <div className={classes.root}>
        <Typography variant="h5">Reserved requests</Typography>
        <Divider style={{ marginBottom: 40 }} />
        <Grid container spacing={3}>
          <Grid item xs={12} md={8}>
            <Paper className={fixedHeightPaper}>
              <ListReservedRequests
                setSelecetedRequestId={setSelecetedRequestId}
              />
            </Paper>
          </Grid>
          <Grid item xs={12} md={4}>
            {selectedRequestId !== -1 && (
              <MessageBox
                selectedRequestId={selectedRequestId}
                user={user}
              ></MessageBox>
            )}
          </Grid>
        </Grid>
      </div>
    </>
  );
};
const mapStateToProps = (state) => ({
  comments: state.comment.advertisementComments,
  user: state.user.user,
});

export default withRouter(connect(mapStateToProps, {})(MessageTab));
