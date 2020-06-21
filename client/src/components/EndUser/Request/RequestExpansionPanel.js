import React, { useState, useEffect } from "react";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Grid from "@material-ui/core/Grid";
import RequestActions from "./RequestActions";
import Paper from "@material-ui/core/Paper";
import Divider from "@material-ui/core/Divider";

const RequestExpansionPanel = ({ title, color, requests }) => {
  const [hoveredId, setHoveredId] = useState(-1);

  const getDateCreated = (req) => {
    const date = new Date(req.creationDate);
    return `${
      date.getDay() + 1
    }/${date.getMonth()}/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`;
  };

  const getRequests = () => {
    const retData = [];
    let bundleRequests = [];
    let currContainerId = -1;

    let i = 0;
    while (i < requests.length) {
      if (requests[i].containerId == -1)
        retData.push(createRequest(requests[i]));
      else if (currContainerId != requests[i].containerId) {
        currContainerId = requests[i].containerId;
        if (bundleRequests.length > 0)
          retData.push(createBundle(bundleRequests));
        bundleRequests = [requests[i]];
      } else {
        bundleRequests.push(requests[i]);
        if (i === requests.length - 1) {
          retData.push(createBundle(bundleRequests));
        }
      }
      i++;
    }
    return retData;
  };

  function getColor() {
    return (
      "hsl(" +
      360 * Math.random() +
      "," +
      (25 + 70 * Math.random()) +
      "%," +
      (85 + 10 * Math.random()) +
      "%)"
    );
  }

  const [colorSelected, setColorSelected] = useState("#ffffff");

  const colorChanger = () => {
    setColorSelected(getColor());
  };

  useEffect(() => {
    setInterval(colorChanger, 4000);
  }, []);

  const createRequest = (r) => (
    <Grid item md={6}>
      <Paper
        elevation={4}
        style={{
          display: "flex",
          justifyContent: "space-around",
          position: "relative",
          padding: "22px",
          background:
            hoveredId != -1 && hoveredId != r.id
              ? "#00000013"
              : hoveredId === r.id
              ? colorSelected
              : "#ffffff",
          transition:
            "background " + (hoveredId === r.id ? "2s" : "0.33s") + " ease",
        }}
        className="CreatedRequests-request"
        onMouseEnter={() => setHoveredId(r.id)}
        onMouseLeave={() => setHoveredId(-1)}
      >
        <span style={{ color: "#a6a6a6", fontWeight: 500 }}>
          {getDateCreated(r)}
        </span>
        <span style={{ color: "#363636", fontWeight: 500 }}>
          {r.brandName + " " + r.modelName}
        </span>
        <span>{r.userEmail}</span>
        <span>
          <RequestActions
            visibility={r.id == hoveredId}
            chatName={
              r.id + " " + r.brandName + " " + r.modelName + " " + r.userEmail
            }
            roomId={"" + r.id}
            sendTo={r.userEmail}
          />
        </span>
      </Paper>
    </Grid>
  );

  const createBundle = (requests) => {
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
                  : "#ffffff",
              transition: "background 0.33s ease",
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
                "Bundle " +
                requests[0].containerId +
                " " +
                requests[0].userEmail
              }
              roomId={"b" + requests[0].containerId}
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
                    hoveredId != -1 &&
                    hoveredId != "*" + requests[0].containerId
                      ? "#00000013"
                      : "#ffffff",
                  transition: "background 0.33s ease",
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
  };

  return (
    <ExpansionPanel>
      <ExpansionPanelSummary
        expandIcon={<ExpandMoreIcon />}
        aria-label="Expand"
        aria-controls="additional-actions3-content"
        id="additional-actions3-header"
        style={{ color: color, fontWeight: 500, fontSize: 18 }}
      >
        {title}
      </ExpansionPanelSummary>
      <ExpansionPanelDetails>
        <Grid container spacing={3}>
          {getRequests()}
        </Grid>
      </ExpansionPanelDetails>
    </ExpansionPanel>
  );
};

export default RequestExpansionPanel;
