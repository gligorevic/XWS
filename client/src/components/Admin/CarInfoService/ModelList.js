import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import AddIcon from "@material-ui/icons/Add";
import InfoIcon from "@material-ui/icons/Info";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";
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
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

const ModelList = ({
  allModels,
  setSelectedModel,
  selectedModel,
  isSelectedBrand,
  handleClickModel,
  handleOpenDialog,
  setAllModels,
}) => {
  const classes = useStyles();

  const [editDialogOpened, setEditDialogOpened] = useState(false);

  const [text, setText] = useState("");
  const [model, setModel] = useState(null);

  const handleListItemClick = (event, model) => {
    if (selectedModel && selectedModel.id === model.id) setSelectedModel(null);
    else {
      setSelectedModel(model);
      handleClickModel(event, model);
    }
  };

  const handleClose = (e) => {
    setEditDialogOpened(false);
    setModel(null);
  };

  const handleChange = (e) => {
    setText(e.target.value);
  };

  const handleOpenEditDialog = (e, entity) => {
    e.stopPropagation();
    setEditDialogOpened(true);
    setText(entity.modelName);
    setModel(entity);
  };

  const handleSubmitEdit = async () => {
    setEditDialogOpened(false);
    const response = await Axios.put(`car-info/model/${model.id}`, text, {
      headers: { "Content-Type": "text/plain" },
    });

    if (response.status >= 200 && response.status < 300) {
      setAllModels((oldModleList) =>
        oldModleList.map((modelItem) =>
          modelItem.id === model.id ? response.data : modelItem
        )
      );
      setModel(null);
    }
  };

  return (
    <div className={classes.root}>
      {isSelectedBrand && allModels.length > 0 ? (
        <List
          component="nav"
          aria-label="main mailbox folders"
          style={{ height: 400, overflowY: "scroll" }}
        >
          {allModels.map((model) => {
            return (
              <ListItem
                button
                selected={selectedModel && selectedModel.id === model.id}
                onClick={(event) => handleListItemClick(event, model)}
              >
                <ListItemText primary={model.modelName} />
                <IconButton
                  style={{ float: "right" }}
                  onClick={(e) => handleOpenEditDialog(e, model)}
                  color="primary"
                  aria-label="add to shopping cart"
                >
                  <EditIcon />
                </IconButton>
              </ListItem>
            );
          })}
        </List>
      ) : (
        <div
          style={{
            minHeight: 400,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            color: "#777",
          }}
        >
          <InfoIcon />
          {allModels.length === 0
            ? "Selected brand has not models yet."
            : "Select one brand"}
        </div>
      )}

      <List>
        <Divider />
        <ListItem
          button
          disabled={!isSelectedBrand}
          onClick={(e) => handleOpenDialog(e, 1)}
        >
          <ListItemIcon>
            <AddIcon />
          </ListItemIcon>
          <ListItemText primary="Add new model" />
        </ListItem>
      </List>

      <Dialog
        open={editDialogOpened}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle>Edit model</DialogTitle>
        <DialogContent style={{ minWidth: 550 }}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            onChange={handleChange}
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

const mapStateToProps = (state) => ({
  currentUser: state.user.user,
});

export default connect(mapStateToProps)(ModelList);
