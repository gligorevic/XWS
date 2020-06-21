import React, { useState, useEffect } from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Rating from "@material-ui/lab/Rating";
import Axios from "axios";

import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

const GradeDialog = ({ request, open, setOpen, user }) => {
  const [grade, setGrade] = useState(0);
  const [fetchedAndGreatherThanZero, setFetchedAndGreatherThanZero] = useState(
    false
  );

  console.log(user);
  console.log(request);
  useEffect(() => {
    (async () => {
      try {
        const gradeResp = await Axios.get(`/feedback/grade/${request.id}`);
        setGrade(gradeResp.data);
        gradeResp.data > 0
          ? setFetchedAndGreatherThanZero(true)
          : setFetchedAndGreatherThanZero(false);
      } catch (e) {
        console.log(e);
      }
    })();
  }, []);

  const handleClose = () => {
    setOpen(-1);
  };

  const handleSubmit = async () => {
    const response = await Axios.post(`/feedback/grade`, {
      grade,
      requestId: request.id,
      username: user.username,
    });

    if (response && response.status >= 200 && response.status < 300) {
      setOpen(-1);
    }
  };

  const setNewGrade = (e, newVal) => {
    setGrade(newVal);
  };

  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Grade"}</DialogTitle>
        <DialogContent>
          {user &&
            request &&
            user.role.some((r) => r.name === "ROLE_ENDUSER") &&
            user.username === request.userSentRequest && (
              <>
                <Rating
                  readOnly={grade > 0 && fetchedAndGreatherThanZero}
                  name="size-large"
                  value={grade}
                  onChange={setNewGrade}
                  size="large"
                />
                <Button
                  disabled={grade > 0 && fetchedAndGreatherThanZero}
                  size="small"
                  color="primary"
                  style={{ float: "right" }}
                  onClick={handleSubmit}
                >
                  Rate
                </Button>
              </>
            )}
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose} color="primary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default withRouter(connect(mapStateToProps, {})(GradeDialog));
