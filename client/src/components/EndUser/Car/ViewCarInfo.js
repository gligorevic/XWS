import React from "react";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import Divider from "@material-ui/core/Divider";

export default function ViewCarInfo({ car }) {
  return (
    <Paper style={{ padding: 50, marginBottom: 40 }} elevation={3}>
      <Typography
        variant="h4"
        component="h2"
        style={{ marginBottom: 20, marginLeft: 13 }}
      >
        Car info
      </Typography>
      {car && (
        <>
          <List disablePadding>
            <ListItem>
              <ListItemText primary="Gear shift" />
              <Typography variant="subtitle1">{car.gearShiftName}</Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Fuel type" />
              <Typography variant="subtitle1">{car.fuelTypeName}</Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Body" />
              <Typography variant="subtitle1">{car.bodyName}</Typography>
            </ListItem>
            <ListItem>
              <ListItemText primary="Km passed" />
              <Typography variant="subtitle1">{car.kmPassed}</Typography>
            </ListItem>
            <Divider />
          </List>
        </>
      )}
    </Paper>
  );
}
