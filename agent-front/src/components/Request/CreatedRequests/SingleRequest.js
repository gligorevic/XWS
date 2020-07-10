import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";
import RequestActions from "./RequestActions";
import Paper from "@material-ui/core/Paper";

export default function SingleRequest({
  request,
  hoveredId,
  setHoveredId,
  setOpenedDialog,
  colorSelected,
  show,
}) {
  const getDateCreated = (req) => {
    const date = new Date(req.creationDate);
    return `${
      date.getDay() + 1
    }/${date.getMonth()}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
  };

  return (
    <Grid item md={6}>
      <Paper
        elevation={4}
        style={{
          display: "flex",
          justifyContent: "space-around",
          position: "relative",
          padding: "22px",
          background:
            hoveredId != -1 && hoveredId != request.id
              ? "#00000013"
              : hoveredId === request.id
              ? colorSelected
              : "#ffffff",
          transition:
            "background " +
            (hoveredId === request.id ? "2s" : "0.33s") +
            " ease",
        }}
        className="CreatedRequests-request"
        onMouseEnter={() => setHoveredId(request.id)}
        onMouseLeave={() => setHoveredId(-1)}
      >
        <span style={{ color: "#a6a6a6", fontWeight: 500 }}>
          {getDateCreated(request)}
        </span>
        <span style={{ color: "#363636", fontWeight: 500 }}>
          {request.brandName + " " + request.modelName}
        </span>
        <span>{request.userEmail}</span>
        <span>
          <RequestActions
            visibility={request.id == hoveredId}
            chatName={
              request.id +
              " " +
              request.brandName +
              " " +
              request.modelName +
              " " +
              request.userEmail
            }
            roomId={"" + request.id}
            sendTo={request.userEmail}
            setOpenedDialog={setOpenedDialog}
            show={show}
          />
        </span>
      </Paper>
    </Grid>
  );
}
