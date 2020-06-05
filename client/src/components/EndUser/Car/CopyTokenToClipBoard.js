import React, { useState, useRef } from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import { IconButton } from "@material-ui/core";
import CheckCircleOutlineIcon from "@material-ui/icons/CheckCircleOutline";
import FileCopyIcon from "@material-ui/icons/FileCopy";
import ToolTip from "@material-ui/core/ToolTip";

export default function AlertDialog({ open, setToken, token }) {
  const [copySuccess, setCopySuccess] = useState(false);

  const handleClose = () => {
    setToken(null);
  };

  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Car location token"}
        </DialogTitle>
        <DialogContent
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <DialogContentText style={{ wordWrap: "break-word", width: 400 }}>
            {token}
          </DialogContentText>
          {!copySuccess ? (
            <ToolTip title="Copy to clipboard">
              <IconButton
                onClick={() => {
                  navigator.clipboard.writeText(token);
                  setCopySuccess(true);
                }}
              >
                <FileCopyIcon fontSize="large" />
              </IconButton>
            </ToolTip>
          ) : (
            <IconButton>
              <CheckCircleOutlineIcon
                fontSize="large"
                style={{ color: "#4caf50" }}
              />
            </IconButton>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
