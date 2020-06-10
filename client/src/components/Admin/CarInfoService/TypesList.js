import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import AddIcon from "@material-ui/icons/Add";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/Grid";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Axios from "axios";

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
  setEditText,
  setAllFuelTypes,
  setAllBodyTypes,
  setAllGearShiftTypes,
}) => {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);
  const [editDialogOpened, setEditDialogOpened] = useState(false);

  const [text, setText] = useState("");
  const [witchType, setWitchType] = useState("");
  const [type, setType] = useState(null);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleListItemClick = (event, type) => {
    handleClickType(event, type);
  };

  const handleClose = (e) => {
    setEditDialogOpened(false);
    setType(null);
  };

  const handleChangeEdit = (e) => {
    setText(e.target.value);
  };

  const handleOpenEditDialog = (e, witch, entity) => {
    e.stopPropagation();
    setEditDialogOpened(true);
    setType(entity);
    setWitchType(witch);
    switch (witch) {
      case "fuel":
        setText(entity.fuelTypeName);
        break;
      case "gear":
        setText(entity.gearShiftName);
        break;
      case "body":
        setText(entity.bodyTypeName);
        break;
    }
  };

  const handleSubmitEdit = async (e) => {
    try {
      setEditDialogOpened(false);
      switch (witchType) {
        case "fuel":
          const editedFuel = await Axios.put(
            `/car-info/fuel-type/${type.id}`,
            text,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          if (editedFuel.status >= 200 && editedFuel.status < 300) {
            setAllFuelTypes((oldFuelList) =>
              oldFuelList.map((fuelItem) =>
                fuelItem.id === type.id ? editedFuel.data : fuelItem
              )
            );
            setType(null);
          }
          break;
        case "gear":
          const editedGear = await Axios.put(
            `/car-info/gear-shift-type/${type.id}`,
            text,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          if (editedGear.status >= 200 && editedGear.status < 300) {
            setAllGearShiftTypes((oldGearList) =>
              oldGearList.map((gearItem) =>
                gearItem.id === type.id ? editedGear.data : gearItem
              )
            );
            setType(null);
          }
          break;
        case "body":
          const editedBody = await Axios.put(
            `/car-info/body-type/${type.id}`,
            text,
            {
              headers: { "Content-Type": "text/plain" },
            }
          );
          if (editedBody.status >= 200 && editedBody.status < 300) {
            setAllBodyTypes((oldBodyList) =>
              oldBodyList.map((bodyItem) =>
                bodyItem.id === type.id ? editedBody.data : bodyItem
              )
            );
            setType(null);
          }

          break;
        default:
          break;
      }
    } catch (e) {
      console.log(e);
    }
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
                <>
                  <Grid container>
                    <Grid item md={10} className={classes.listContainer}>
                      <ListItem>
                        <ListItemText primary={fuelType.fuelTypeName} />
                      </ListItem>
                    </Grid>
                    <Grid item md={2} className={classes.listContainer}>
                      <IconButton
                        style={{ float: "right" }}
                        onClick={(e) =>
                          handleOpenEditDialog(e, "fuel", fuelType)
                        }
                        color="primary"
                        aria-label="add to shopping cart"
                      >
                        <EditIcon />
                      </IconButton>
                    </Grid>
                  </Grid>
                </>
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
                <>
                  <Grid container>
                    <Grid item md={10} className={classes.listContainer}>
                      <ListItem>
                        <ListItemText primary={gearShiftType.gearShiftName} />
                      </ListItem>
                    </Grid>
                    <Grid item md={2} className={classes.listContainer}>
                      <IconButton
                        style={{ float: "right" }}
                        onClick={(e) =>
                          handleOpenEditDialog(e, "gear", gearShiftType)
                        }
                        color="primary"
                        aria-label="add to shopping cart"
                      >
                        <EditIcon />
                      </IconButton>
                    </Grid>
                  </Grid>
                </>
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
                <>
                  <Grid container>
                    <Grid item md={10} className={classes.listContainer}>
                      <ListItem>
                        <ListItemText primary={bodyType.bodyTypeName} />
                      </ListItem>
                    </Grid>
                    <Grid item md={2} className={classes.listContainer}>
                      <IconButton
                        style={{ float: "right" }}
                        onClick={(e) =>
                          handleOpenEditDialog(e, "body", bodyType)
                        }
                        color="primary"
                        aria-label="edit type"
                      >
                        <EditIcon />
                      </IconButton>
                    </Grid>
                  </Grid>
                </>
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
      <Dialog
        open={editDialogOpened}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle>Edit type</DialogTitle>
        <DialogContent style={{ minWidth: 550 }}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            onChange={handleChangeEdit}
            value={text}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Disagree
          </Button>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            onClick={handleSubmitEdit}
          >
            Submit
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default TypeList;
