import React, { useEffect } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import AddIcon from "@material-ui/icons/Add";
import EditIcon from "@material-ui/icons/Edit";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";

import { getAllBrands } from "../../../store/actions/carInfo";

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.paper,
  },
}));

const TypeList = ({
  allFuelTypes,
  allGearShiftTypes,
  allBodyTypes,
  handleClickType,
}) => {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleListItemClick = (event, type) => {
    handleClickType(event, type);
  };

  return (
    <div className={classes.root}>
      <Tabs
        value={value}
        onChange={handleChange}
        indicatorColor="primary"
        textColor="primary"
        centered
      >
        <Tab label="Fuel type" />
        <Tab label="Gearshift type" />
        <Tab label="Body type" />
      </Tabs>
      {value === 0 && (
        <>
          <List
            component="nav"
            aria-label="main mailbox folders"
            style={{ height: 352, overflowY: "scroll" }}
          >
            {allFuelTypes.map((fuelType) => {
              return (
                <ListItem>
                  <ListItemText primary={fuelType.fuelTypeName} />
                </ListItem>
              );
            })}
          </List>
          <List>
            <Divider />

            <ListItem
              button
              onClick={(event) => handleListItemClick(event, "newFuelType")}
            >
              <ListItemIcon>
                <AddIcon />
              </ListItemIcon>
              <ListItemText primary="Add new type" />
            </ListItem>
          </List>
        </>
      )}
      {value === 1 && (
        <>
          <List
            component="nav"
            aria-label="main mailbox folders"
            style={{ height: 352, overflowY: "scroll" }}
          >
            {allGearShiftTypes.map((gearShiftType) => {
              return (
                <ListItem>
                  <ListItemText primary={gearShiftType.gearShiftName} />
                </ListItem>
              );
            })}
          </List>
          <List>
            <Divider />

            <ListItem
              button
              onClick={(event) =>
                handleListItemClick(event, "newGearShiftType")
              }
            >
              <ListItemIcon>
                <AddIcon />
              </ListItemIcon>
              <ListItemText primary="Add new type" />
            </ListItem>
          </List>
        </>
      )}
      {value === 2 && (
        <>
          <List
            component="nav"
            aria-label="main mailbox folders"
            style={{ height: 352, overflowY: "scroll" }}
          >
            {allBodyTypes.map((bodyType) => {
              return (
                <ListItem>
                  <ListItemText primary={bodyType.bodyTypeName} />
                </ListItem>
              );
            })}
          </List>
          <List>
            <Divider />

            <ListItem
              button
              onClick={(event) => handleListItemClick(event, "newBodyType")}
            >
              <ListItemIcon>
                <AddIcon />
              </ListItemIcon>
              <ListItemText primary="Add new type" />
            </ListItem>
          </List>
        </>
      )}
    </div>
  );
};

export default TypeList;
