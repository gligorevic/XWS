import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Axios from "axios";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { format } from "date-fns";

const AcceptDialog = ({
  openAcceptDialog,
  setOpenAcceptDialog,
  request,
  setRequest,
  requestsInBundle,
}) => {
  const handleClose = () => {
    setOpenAcceptDialog(false);
  };

  const handleAgree = async () => {
    setOpenAcceptDialog(false);
    request.paidState = "RESERVED";
    const resp = await Axios.put(`/request/bundle/${request.id}`, request);
  };

  return (
    <div>
      <Dialog
        open={openAcceptDialog}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Are you sure you want to accept this request?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            If you accept this request, all requests bellow will be accepted.
          </DialogContentText>
          {requestsInBundle && (
            <List>
              {requestsInBundle.map((row) => {
                return (
                  <ListItem key={row.id}>
                    <ListItemText style={{ fontSize: 14 }}>
                      {row.brandName + " - " + row.modelName} <br />
                      {format(new Date(row.startDate), "dd MMM yyyy") +
                        "  -  " +
                        format(new Date(row.endDate), "dd MMM yyyy")}
                    </ListItemText>
                  </ListItem>
                );
              })}
            </List>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleAgree} color="primary" variant="contained">
            Agree
          </Button>
          <Button onClick={handleClose} color="primary">
            Disagree
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default AcceptDialog;
