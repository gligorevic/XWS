import React, { useState, useEffect } from "react";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Grid from "@material-ui/core/Grid";
import RequestActions from "./RequestActions";
import Paper from "@material-ui/core/Paper";
import Divider from "@material-ui/core/Divider";

export default function BundleRequest({
  requests,
  hoveredId,
  setHoveredId,
  colorSelected,
}) {
  const getDateCreated = (req) => {
    const date = new Date(req.creationDate);
    return `${
      date.getDay() + 1
    }/${date.getMonth()}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
  };

  return (
    <Grid item md={6}>
      <Paper>
        <div
          style={{
            position: "relative",
            padding: "32px",
            display: "flex",
            justifyContent: "space-around",
            background:
              hoveredId != -1 && hoveredId != "*" + requests[0].containerId
                ? "#00000013"
                : hoveredId === "*" + requests[0].containerId
                ? colorSelected
                : "#ffffff",
            transition:
              "background " +
              (hoveredId === "*" + requests[0].containerId ? "2s" : "0.33s") +
              " ease",
          }}
          onMouseEnter={() => setHoveredId("*" + requests[0].containerId)}
          onMouseLeave={() => setHoveredId(-1)}
        >
          <span style={{ color: "#a6a6a6", fontWeight: 500 }}>
            Bundle {requests[0].containerId}
          </span>
          <span style={{ color: "#a6a6a6", fontWeight: 500 }}>
            {getDateCreated(requests[0])}
          </span>
          <RequestActions
            visibility={"*" + requests[0].containerId == hoveredId}
            chatName={
              "Bundle " + requests[0].containerId + " " + requests[0].userEmail
            }
            roomId={"b" + requests[0].containerId}
            sendTo={requests[0].userEmail}
          />
        </div>
        {requests.map((r) => (
          <>
            <Divider />
            <div
              elevation={4}
              style={{
                display: "flex",
                justifyContent: "space-around",
                padding: "22px",
                background:
                  hoveredId != -1 && hoveredId != "*" + requests[0].containerId
                    ? "#00000013"
                    : hoveredId === "*" + requests[0].containerId
                    ? colorSelected
                    : "#ffffff",
                transition:
                  "background " +
                  (hoveredId === "*" + requests[0].containerId
                    ? "2s"
                    : "0.33s") +
                  " ease",
              }}
              className="CreatedRequests-request"
              onMouseEnter={() => setHoveredId("*" + r.containerId)}
              onMouseLeave={() => setHoveredId(-1)}
            >
              <span style={{ color: "#363636", fontWeight: 500 }}>
                {r.brandName + " " + r.modelName}
              </span>
              <span style={{ color: "#363636", fontWeight: 500 }}>
                {r.userEmail}
              </span>
            </div>
          </>
        ))}
      </Paper>
    </Grid>
  );
}
