import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Rating from "@material-ui/lab/Rating";
import Axios from "axios";

import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";

const GradeDialog = ({
  request,
  openGradeDialog,
  setOpenGradeDialog,
  grade,
  user,
}) => {
  const [gradeValue, setGradeValue] = React.useState(grade);

  const handleClose = () => {
    setOpenGradeDialog(false);
  };

  const handleSubmit = async () => {
    const response = await Axios.post(`/feedback/grade`, gradeValue);

    if (response && response.status >= 200 && response.status < 300) {
      setOpenGradeDialog(false);
    }
  };

  return (
    <div>
      <Dialog
        open={openGradeDialog}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">{"Grade"}</DialogTitle>
        <DialogContent>
          {gradeValue &&
            user &&
            request &&
            user.role.some((r) => r.name === "ROLE_ENDUSER") &&
            user.username === request.userSentRequest && (
              <>
                <Rating
                  readOnly={grade.grade > 0}
                  name="size-large"
                  value={gradeValue.grade}
                  onChange={(event, newValue) => {
                    setGradeValue({ ...gradeValue, grade: newValue });
                  }}
                  size="large"
                />
                <Button
                  disabled={grade.grade > 0}
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
