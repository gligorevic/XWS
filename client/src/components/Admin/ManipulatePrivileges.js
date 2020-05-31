import React from "react";
import Grid from "@material-ui/core/Grid";

import UserSelection from "./UserSelectionForPrivilegeChanging";
import BlockPrivileges from "./BlockPrivileges";

export default function ManipulatePrivileges() {
  return (
    <Grid container spacing={3}>
      <Grid item sm={6}>
        <UserSelection />
      </Grid>
      <Grid item sm={6}>
        <BlockPrivileges />
      </Grid>
    </Grid>
  );
}
