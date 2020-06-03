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
    width: "100%",
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
  const [selectedIndex, setSelectedIndex] = React.useState(null);
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleListItemClick = (event, type, index) => {
    setSelectedIndex(index);
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
      {allFuelTypes && value === 0 && (
        <List component="nav" aria-label="main mailbox folders">
          {allFuelTypes.map((fuelType, index) => {
            return (
              <ListItem
                button
                selected={selectedIndex === index}
                onClick={(event) => handleListItemClick(event, fuelType, index)}
              >
                <ListItemText primary={fuelType.fuelTypeName} />
              </ListItem>
            );
          })}
          <Divider />

          <ListItem
            button
            selected={selectedIndex === allFuelTypes.length}
            onClick={(event) =>
              handleListItemClick(event, "newFuelType", allFuelTypes.length)
            }
          >
            <ListItemIcon>
              <AddIcon />
            </ListItemIcon>
            <ListItemText primary="Add new type" />
          </ListItem>
        </List>
      )}
      {allGearShiftTypes && value === 1 && (
        <List component="nav" aria-label="main mailbox folders">
          {allGearShiftTypes.map((gearShiftType, index) => {
            return (
              <ListItem
                button
                selected={selectedIndex === index}
                onClick={(event) =>
                  handleListItemClick(event, gearShiftType, index)
                }
              >
                <ListItemText primary={gearShiftType.gearShiftName} />
              </ListItem>
            );
          })}
          <Divider />

          <ListItem
            button
            selected={selectedIndex === allGearShiftTypes.length}
            onClick={(event) =>
              handleListItemClick(
                event,
                "newGearShiftType",
                allGearShiftTypes.length
              )
            }
          >
            <ListItemIcon>
              <AddIcon />
            </ListItemIcon>
            <ListItemText primary="Add new type" />
          </ListItem>
        </List>
      )}
      {allBodyTypes && value === 2 && (
        <List component="nav" aria-label="main mailbox folders">
          {allBodyTypes.map((bodyType, index) => {
            return (
              <ListItem
                button
                selected={selectedIndex === index}
                onClick={(event) => handleListItemClick(event, bodyType, index)}
              >
                <ListItemText primary={bodyType.bodyTypeName} />
              </ListItem>
            );
          })}
          <Divider />

          <ListItem
            button
            selected={selectedIndex === allBodyTypes.length}
            onClick={(event) =>
              handleListItemClick(event, "newBodyType", allBodyTypes.length)
            }
          >
            <ListItemIcon>
              <AddIcon />
            </ListItemIcon>
            <ListItemText primary="Add new type" />
          </ListItem>
        </List>
      )}
    </div>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.user.user,
});

export default connect(mapStateToProps)(TypeList);
