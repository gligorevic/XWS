import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Axios from "axios";

const useStyles = makeStyles((theme) => ({}));

function CommentReply({
  requestId,
  user,
  setOpen,
  setOpenFailure,
  setErrorMessage,
  setOpenSuccess,
  request,
}) {
  const classes = useStyles();

  const [state, setState] = React.useState({
    text: "",
    requestId: request.inBundle ? request.containerId : request.id,
    username: user,
    agentUsername: request.userEmail,
    inBundle: request.inBundle,
  });

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let resp;
    if (!request.inBundle) {
      resp = await Axios.post("/feedback/comment", state).catch((error) => {
        if (error.response && error.response.status === 400) {
          setOpen(-1);
          setOpenFailure(true);
          setErrorMessage(error.response.data);
        }
      });
    } else {
      resp = await Axios.post("/feedback/comment/bundle", state).catch(
        (error) => {
          if (error.response && error.response.status === 400) {
            setOpen(-1);
            setOpenFailure(true);
            setErrorMessage(error.response.data);
          }
        }
      );
    }

    if (resp && resp.status >= 200 && resp.status < 300) {
      setState({
        ...state,
        text: "",
      });
      setOpen(-1);
      setOpenSuccess(true);
    }
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
    </div>
  );
}

export default CommentReply;
