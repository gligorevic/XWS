import React from "react";
import Grid from "@material-ui/core/Grid";

import UserSelection from "./UserSelectionForPrivilegeChanging";
import BlockPrivileges from "./BlockPrivileges";

import Axios from "axios";

export default function ManipulatePrivileges() {
  const [selected, setSelected] = React.useState(null);

  const handleClick = async (event, user) => {
    if (selected && user.id == selected.id) setSelected(null);
    else {
      setSelected(user);
      const res = await Axios.get(`/auth/user/blockedPrivileges/${user.id}`);
      console.log(res.data);
    }
  };
  return (
    <Grid container spacing={3}>
      <Grid item sm={12} lg={6}>
        <h2 style={{ textAlign: "center" }}>Select User</h2>
        <UserSelection selected={selected} handleClick={handleClick} />
      </Grid>
      <Grid item sm={12} lg={6}>
        <h2 style={{ textAlign: "center" }}>Change Privileges</h2>
        <BlockPrivileges />
      </Grid>
    </Grid>
  );
}
