import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Divider from "@material-ui/core/Divider";
import Paper from "@material-ui/core/Paper";
import { connect } from "react-redux";
import { getCreatedRequests } from "../../../../store/actions/request";
import "./CreatedRequests.css";
import RequestExpansionPanel from "./RequestExpansionPanel";

const useStyles = makeStyles({});

function CreatedRequests({ getCreatedRequests, user, createdRequests }) {
  const classes = useStyles();
  useEffect(() => {
    getCreatedRequests(user.username);
  }, []);

  return (
    <div style={{ margin: 100 }}>
      <h2 style={{ marginBottom: 5 }}>My created requests</h2>
      <Divider style={{ marginBottom: 15 }} />
      <Paper>
        <RequestExpansionPanel
          title="PAID"
          color="#2196f3"
          requests={createdRequests.filter((r) => r.paidState === "PAID")}
          user={user}
          show={["Comment", "Rate"]}
        />
        <RequestExpansionPanel
          title="RESERVED"
          color="#4caf50"
          requests={createdRequests.filter((r) => r.paidState === "RESERVED")}
          user={user}
          show={["Chat", "Pay", "Cancel"]}
        />
        <RequestExpansionPanel
          title="PENDING"
          color="#ff9800"
          requests={createdRequests.filter((r) => r.paidState === "PENDING")}
          user={user}
          show={["Cancel"]}
        />
        <RequestExpansionPanel
          title="CANCELED"
          color="#f44336"
          requests={createdRequests.filter((r) => r.paidState === "CANCELED")}
          user={user}
          show={[]}
        />
      </Paper>
    </div>
  );
}

const mapStateToProps = (state) => ({
  user: state.user.user,
  createdRequests: state.request.createdRequests,
});

export default connect(mapStateToProps, { getCreatedRequests })(
  CreatedRequests
);
