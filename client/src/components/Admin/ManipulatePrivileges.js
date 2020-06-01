import React, { useEffect } from "react";
import Grid from "@material-ui/core/Grid";

import UserSelection from "./UserSelectionForPrivilegeChanging";
import BlockPrivileges from "./BlockPrivileges";

import Paper from "@material-ui/core/Paper";
import Axios from "axios";
import InfoIcon from "@material-ui/icons/Info";

export default function ManipulatePrivileges() {
  const [selected, setSelected] = React.useState(null);
  const [blockedPrivileges, setBlockedPrivileges] = React.useState([]);
  const [allPrivileges, setAllPrivileges] = React.useState([]);
  const [
    disableBlockingPrivileges,
    setDisableBlockingPrivileges,
  ] = React.useState(false);

  useEffect(() => {
    (async () => {
      const res = await Axios.get("/auth/privilege");
      setAllPrivileges(res.data);
    })();
  }, []);

  const handleClick = async (event, user, disableBlocking) => {
    if (selected && user.id == selected.id) setSelected(null);
    else {
      setBlockedPrivileges([]);
      setSelected(user);
      const res = await Axios.get(`/auth/user/${user.id}/privilege`);
      setBlockedPrivileges(res.data);
      setDisableBlockingPrivileges(disableBlocking);
    }
  };

  const handleChangeBlockedPrivileges = async (privileges) => {
    const newBlockedPrivilegesIds = privileges.map((p) => p.id);
    if (selected) {
      const res = await Axios.put(
        `/auth/user/${selected.id}/privilege`,
        newBlockedPrivilegesIds
      );
      setBlockedPrivileges(privileges);
    }
  };

  return (
    <Grid container spacing={1}>
      <Grid item sm={12} lg={5}>
        <h2 style={{ textAlign: "center" }}>Select User</h2>
        <UserSelection
          selected={selected}
          handleClick={handleClick}
          setSelected={setSelected}
        />
      </Grid>
      <Grid item sm={12} lg={7}>
        <h2 style={{ textAlign: "center" }}>Change Privileges</h2>
        {selected && !disableBlockingPrivileges ? (
          <BlockPrivileges
            allPrivileges={allPrivileges}
            blockedPrivileges={blockedPrivileges}
            handleChangeBlockedPrivileges={handleChangeBlockedPrivileges}
          />
        ) : (
          <Paper
            style={{
              height: 447,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              fontSize: 24,
              color: "#434343",
            }}
          >
            <InfoIcon fontSize="large" />
            {disableBlockingPrivileges
              ? "You can't block your privileges"
              : "Select user to manipulate privileges"}
          </Paper>
        )}
      </Grid>
    </Grid>
  );
}
