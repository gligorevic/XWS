import React from "react";
import Paper from "@material-ui/core/Paper";
import { Button } from "@material-ui/core";
import ClearIcon from "@material-ui/icons/Clear";
import IconButton from "@material-ui/core/IconButton";
import BusinessIcon from "@material-ui/icons/Business";
import { withRouter } from "react-router";

function Popper({ open, setPopupClosed, history }) {
  return (
    <Paper
      style={{
        position: "absolute",
        display: open ? "block" : "none",
        bottom: "0",
        left: "0",
        paddingTop: 2,
        minWidth: 220,
      }}
      className="paperPopoverTransition"
    >
      <div
        style={{
          margin: 0,
          display: "flex",
          justifyContent: "space-between",
          padding: 0,
        }}
      >
        <IconButton
          disabled
          style={{ color: "rgba(0,0,0,0.54)", paddingLeft: 10 }}
        >
          <BusinessIcon />
        </IconButton>
        <IconButton onClick={(e) => setPopupClosed(true)}>
          <ClearIcon />
        </IconButton>
      </div>
      <p style={{ padding: "4px 10px", marginTop: 0 }}>
        Confirmation of company is denied, send request again?
      </p>
      <Button
        style={{
          backgroundColor: "#ff9800",
          color: "white",
          float: "right",
          marginRight: 10,
          marginBottom: 10,
        }}
        size="small"
        onClick={(e) => history.push({ pathname: "/user", state: { tab: 11 } })}
      >
        Do it
      </Button>
    </Paper>
  );
}

export default withRouter(Popper);
