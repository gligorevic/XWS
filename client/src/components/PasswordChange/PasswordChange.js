import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { connect } from "react-redux";
import ThumbUpIcon from "@material-ui/icons/ThumbUp";
import Axios from "axios";

const useStyles = makeStyles((theme) => ({
  "@global": {
    body: {
      backgroundColor: theme.palette.common.white,
    },
  },
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

const ChangePassword = ({ currentUser }) => {
  const classes = useStyles();
  const [promenjena, setPromenjena] = useState(null);
  const [state, setState] = useState({
    confirmedPassword: "",
    newPassword: "",
    oldPassword: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const resp = await Axios.put("/auth/password/change", {
      newPassword: state.newPassword,
      username: currentUser.username,
      oldPassword: state.oldPassword,
    });
    setState({
      confirmedPassword: "",
      newPassword: "",
      oldPassword: "",
    });
    setPromenjena(resp.data);
  };

  const checkPasswordFormat = () =>
    !state.newPassword.match(
      /^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,25}$/
    );

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        {promenjena ? (
          <div style={{ textAlign: "center" }}>
            <ThumbUpIcon style={{ fontSize: 155, color: "#4caf50" }} />
            <Typography component="h4" variant="h3">
              Successfully changed password
            </Typography>
          </div>
        ) : (
          <>
            <Typography component="h1" variant="h3">
              Change password
            </Typography>

            <form className={classes.form} noValidate onSubmit={handleSubmit}>
              <TextField
                error={promenjena === false}
                helperText={promenjena === false && "Enter correct password"}
                value={state.oldPassword}
                onChange={handleChange}
                variant="outlined"
                margin="normal"
                required
                fullWidth
                type="password"
                id="password-old"
                label="Current password"
                name="oldPassword"
                autoFocus
              />
              <TextField
                value={state.newPassword}
                error={checkPasswordFormat() && state.newPassword.length > 0}
                helperText={
                  checkPasswordFormat() &&
                  state.newPassword.length > 0 &&
                  "Password must have one number, one upper-case letter and one lower-case letter and one special char."
                }
                onChange={handleChange}
                variant="outlined"
                margin="normal"
                required
                fullWidth
                type="password"
                id="password-n"
                label="New password"
                name="newPassword"
              />
              <TextField
                error={
                  state.confirmedPassword !== state.newPassword &&
                  state.newPassword.length !== 0
                }
                helperText={
                  state.confirmedPassword !== state.newPassword &&
                  "Passwords must match"
                }
                value={state.confirmedPassword}
                onChange={handleChange}
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="confirmedPassword"
                label="Confirm password"
                type="password"
                id="password"
              />
              <Button
                id="potvrda"
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                disabled={
                  state.confirmedPassword !== state.newPassword ||
                  checkPasswordFormat()
                }
              >
                Change Password
              </Button>
            </form>
          </>
        )}
      </div>
    </Container>
  );
};

function mapStateToProps(state) {
  return {
    currentUser: state.user.user,
  };
}

export default connect(mapStateToProps, null)(ChangePassword);
