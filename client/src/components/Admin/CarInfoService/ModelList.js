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
}) => {
  const classes = useStyles();

  const [editDialogOpened, setEditDialogOpened] = useState(false);

  const [text, setText] = useState("");

  const handleListItemClick = (event, model) => {
    if (selectedModel && selectedModel.id === model.id) setSelectedModel(null);
    else {
      setSelectedModel(model);
      handleClickModel(event, model);
    }
  };

  const handleClose = (e) => {
    setEditDialogOpened(false);
  };

  const handleChange = (e) => {
    setText(e.target.value);
  };

  const handleOpenEditDialog = (e, entity) => {
    setEditDialogOpened(true);
    setText(entity.modelName);
  };

  const handleSubmitEdit = async (modelId) => {
    setEditDialogOpened(false);
    const response = await Axios.put(`/model/${modelId}`, text);

    if (response.status >= 200 && response.status < 300) {
      console.log(response.data);
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
              <>
                <Grid container>
                  <Grid item md={10} className={classes.listContainer}>
                    <ListItem
                      button
                      selected={selectedModel && selectedModel.id === model.id}
                      onClick={(event) => handleListItemClick(event, model)}
                    >
                      <ListItemText primary={model.modelName} />
                    </ListItem>
                  </Grid>
                  <Grid item md={2} className={classes.listContainer}>
                    <IconButton
                      onClick={(e) => handleOpenEditDialog(e, model)}
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
