import React, { useState } from "react";
import { withRouter } from "react-router";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Axios from "axios";
import { setReservationPeriods } from "../../store/actions/advertisement";

const useStyles = makeStyles((theme) => ({}));

function Synchronization({ requestId, setOpen }) {
  const classes = useStyles();

  const [carDisable, setCarDisable] = React.useState(false);
  const [adDisable, setAdDisable] = React.useState(true);
  const [pricelistDisable, setPricelistDisable] = React.useState(true);
  const [itemDisable, setItemDisable] = React.useState(true);
  const [containerDisable, setContainerDisable] = React.useState(true);
  const [requestDisable, setRequestDisable] = React.useState(true);
  const [reportDisable, setReportDisable] = React.useState(true);
  const [rpDisable, setrpDisable] = React.useState(true);
  const [commentDisable, setCommentsDisable] = React.useState(true);
  const [gradeDisable, setGradeDisable] = React.useState(true);

  const handleCars = async (e) => {
    const resp = await Axios.put("synchronize/cars");
    if (resp && resp.status == 200) {
      console.log(resp);
      setAdDisable(false);
    }
  };

  const handleAdvertisements = async (e) => {
    const resp = await Axios.put("synchronize/ad");
    if (resp && resp.status == 200) {
      console.log(resp);
      setPricelistDisable(false);
    }
  };

  const handlePricelists = async (e) => {
    const resp = await Axios.put("synchronize/pricelists");
    if (resp && resp.status == 200) {
      console.log(resp);
      setItemDisable(false);
    }
  };

  const handlePricelistItems = async (e) => {
    const resp = await Axios.put("synchronize/pricelistItems");
    if (resp && resp.status == 200) {
      console.log(resp);
      setContainerDisable(false);
    }
  };

  const handleContainers = async (e) => {
    const resp = await Axios.put("synchronize/containers");
    if (resp && resp.status == 200) {
      console.log(resp);
      setRequestDisable(false);
    }
  };

  const handleRequests = async (e) => {
    const resp = await Axios.put("synchronize/requests");
    if (resp && resp.status == 200) {
      console.log(resp);
      setReportDisable(false);
    }
  };

  const handleReports = async (e) => {
    const resp = await Axios.put("synchronize/reports");
    if (resp && resp.status == 200) {
      console.log(resp);
      setrpDisable(false);
    }
  };

  const handleReservationPeriods = async (e) => {
    const resp = await Axios.put("synchronize/reservationPeriods");
    if (resp && resp.status == 200) {
      console.log(resp);
      setCommentsDisable(false);
    }
  };
  const handleComments = async (e) => {
    const resp = await Axios.put("synchronize/comments");
    if (resp && resp.status == 200) {
      console.log(resp);
      setGradeDisable(false);
    }
  };

  const handleGrades = async (e) => {
    const resp = await Axios.put("synchronize/grades");
    if (resp && resp.status == 200) {
      console.log(resp);
    }
  };

  return (
    <div>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleCars}
        disabled={carDisable}
      >
        Cars
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleAdvertisements}
        disabled={adDisable}
      >
        Advertisements
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handlePricelists}
        disabled={pricelistDisable}
      >
        Pricelists
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handlePricelistItems}
        disabled={itemDisable}
      >
        Pricelist items
      </Button>

      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleContainers}
        disabled={containerDisable}
      >
        Request containers
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleRequests}
        disabled={requestDisable}
      >
        Requests
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleReports}
        disabled={reportDisable}
      >
        Reports
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleReservationPeriods}
        disabled={rpDisable}
      >
        Reservation periods
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleComments}
        disabled={commentDisable}
      >
        Comments
      </Button>
      <Button
        color="primary"
        style={{ float: "left" }}
        onClick={handleGrades}
        disabled={gradeDisable}
      >
        Grades
      </Button>
    </div>
  );
}

export default withRouter(Synchronization);
