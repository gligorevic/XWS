import React, { useState } from "react";
import { withRouter } from "react-router";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Axios from "axios";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";

const useStyles = makeStyles((theme) => ({}));

function CommentReply({ requestId, user, setOpenCommentDialog }) {
  const classes = useStyles();

  const [state, setState] = React.useState({
    text: "",
    requestId: requestId,
    username: user,
  });

  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const resp = await Axios.post("/feedback/comment", state).catch((error) => {
      if (error.response && error.response.status === 400) {
        setOpenCommentDialog(false);
        setOpenFailure(true);
        setErrorMessage(error.response.data);
      }
    });
    if (resp && resp.status >= 200 && resp.status < 300) {
      console.log("uslo");
      setState({
        ...state,
        text: "",
      });
      setOpenCommentDialog(false);
      setOpenSuccess(true);
    }
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseError = () => {
    setOpenFailure(false);
  };

  return (
    <div>
      <TextField
        id="outlined-multiline-static"
        label="Add comment"
        multiline
        rows={4}
        variant="outlined"
        fullWidth
        name="text"
        value={state.text}
        onChange={handleChangeTextField}
      />
      <Button
        size="small"
        color="primary"
        style={{ float: "right" }}
        onClick={handleSubmit}
      >
        Send
      </Button>
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
}

export default withRouter(CommentReply);
