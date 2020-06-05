import React from "react";
import AddCar from "../AddCar";
import AppBar from "@material-ui/core/AppBar";
import Dialog from "@material-ui/core/Dialog";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Typography from "@material-ui/core/Typography";

export default function AddCarFullScreen({ open, setOpen }) {
  return (
    <Dialog fullScreen open={open} onClose={() => setOpen(false)}>
      <AppBar>
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            onClick={() => setOpen(false)}
            aria-label="close"
          >
            <CloseIcon />
          </IconButton>
          <Typography variant="h6">Add advertisement</Typography>
        </Toolbar>
      </AppBar>
      <div style={{ padding: 120 }}>
        <AddCar />
      </div>
    </Dialog>
  );
}
