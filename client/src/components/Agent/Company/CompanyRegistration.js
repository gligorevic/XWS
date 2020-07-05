import React, { useState, useEffect } from "react";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import MuiPhoneInput from "material-ui-phone-number";
import Axios from "axios";
import { connect } from "react-redux";
import { Button } from "@material-ui/core";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";

function CompanyRegistration({ user }) {
  const [companyState, setCompanyState] = useState({
    companyName: "",
    registrationNumber: "",
    phoneNumber: "",
    address: "",
  });
  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong, please try later"
  );

  useEffect(() => {
    (async () => {
      try {
        const res = await Axios.get(`/auth/user/${user.id}/company`);
        res.status >= 200 && res.status < 300 && setCompanyState(res.data);
      } catch (err) {
        console.log(err);
      }
    })();
  }, []);

  const handleChangeCompany = (e) => {
    setCompanyState({ ...companyState, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    try {
      const res = await Axios.put(
        `/auth/user/${user.id}/company/reregister`,
        companyState
      );
      if (res.status >= 200 && res.status < 300) {
        setOpenSuccess(true);
      }
    } catch (err) {
      console.log(err);
      setOpenFailure(true);
    }
  };

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };
  const handleCloseError = () => {
    setOpenFailure(false);
  };

  return (
    <>
      <Paper style={{ padding: 30, margin: "5% 20%" }} elevation={5}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <h2 style={{ margin: 5, textAlign: "center" }}>
              Company Informations
            </h2>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              required
              fullWidth
              label="Company name"
              name="companyName"
              value={companyState.companyName}
              onChange={handleChangeCompany}
              id="companyName"
            ></TextField>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              required
              fullWidth
              label="Registration number"
              name="registrationNumber"
              value={companyState.registrationNumber}
              onChange={handleChangeCompany}
              id="registrationNumber"
            ></TextField>
          </Grid>
          <Grid item xs={12} sm={6}>
            <MuiPhoneInput
              fullWidth
              label="Phone number *"
              variant="outlined"
              onChange={(phoneNumber) =>
                setCompanyState((oldState) => ({
                  ...oldState,
                  phoneNumber,
                }))
              }
              defaultCountry="rs"
              value={companyState.phoneNumber}
              regions={"europe"}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              required
              fullWidth
              label="Address"
              value={companyState.address}
              onChange={handleChangeCompany}
              name="address"
              id="address"
            ></TextField>
          </Grid>
          <Grid item xs={12} style={{ padding: "0 30%", margin: "15px 0px" }}>
            <Button
              variant="contained"
              color="primary"
              fullWidth
              onClick={handleSubmit}
            >
              Register Company
            </Button>
          </Grid>
        </Grid>
      </Paper>

      <Snackbar
        open={openSuccess}
        autoHideDuration={2000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Successfully sent company data.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openFailure}
        autoHideDuration={3500}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </>
  );
}

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default connect(mapStateToProps, null)(CompanyRegistration);
