import React, { useState } from "react";
import { withRouter } from "react-router";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Axios from "axios";

const useStyles = makeStyles((theme) => ({}));

function CommentReply({ requestId, setOpen }) {
  const classes = useStyles();

  const [state, setState] = React.useState({
    text: "",
    requestId: requestId,
    creationDate: new Date(),
    userEmail: "agent@gmail.com",
  });

  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    const resp = await Axios.post("/comment", state);
    if (resp.status == 201) {
      console.log("uslo");
      setState({
        ...state,
        text: "",
      });
      setOpen(false);
    }
  };

  return (
    <div>
      <TextField
        id="outlined-multiline-static"
        label="Reply"
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

export default withRouter(CommentReply);
