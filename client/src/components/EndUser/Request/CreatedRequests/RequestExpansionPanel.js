import React, { useState, useEffect } from "react";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Grid from "@material-ui/core/Grid";
import GradeDialog from "../../../Dialogs/Grade/GradeDialog";
import CommentDialog from "../../../Dialogs/Comment/CommentDialog";
import SingleRequest from "./SingleRequest";
import BundleRequest from "./BundleRequest";

const RequestExpansionPanel = ({ title, color, requests, user, show }) => {
  const [hoveredId, setHoveredId] = useState(-1);

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
  const [openedDialog, setOpenedDialog] = useState(-1);

  const colorChanger = () => {
    setColorSelected(getColor());
  };

  useEffect(() => {
    setInterval(colorChanger, 4000);
  }, []);

  const createRequest = (r) => (
    <SingleRequest
      colorSelected={colorSelected}
      request={r}
      hoveredId={hoveredId}
      setHoveredId={setHoveredId}
      setOpenedDialog={setOpenedDialog}
      show={show}
    />
  );

  const createBundle = (requests) => (
    <BundleRequest
      colorSelected={colorSelected}
      requests={requests}
      hoveredId={hoveredId}
      setHoveredId={setHoveredId}
      setOpenedDialog={setOpenedDialog}
      show={show}
    />
  );

  return (
    <>
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
      {openedDialog !== -1 && !openedDialog.startsWith("c") && (
        <GradeDialog
          user={user}
          open={openedDialog !== -1}
          setOpen={setOpenedDialog}
          request={
            openedDialog.startsWith("b")
              ? requests.find((r) => r.containerId == openedDialog.slice(1))
              : requests.find((r) => r.id == openedDialog)
          }
        ></GradeDialog>
      )}
      {openedDialog !== -1 && openedDialog.startsWith("c") && (
        <CommentDialog
          user={user}
          open={openedDialog !== -1}
          setOpen={setOpenedDialog}
          request={
            openedDialog.startsWith("cb")
              ? requests.find((r) => r.containerId == openedDialog.slice(2))
              : requests.find((r) => r.id == openedDialog.slice(1))
          }
        ></CommentDialog>
      )}
    </>
  );
};

export default RequestExpansionPanel;
