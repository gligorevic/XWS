import React, { useEffect } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import AddIcon from "@material-ui/icons/Add";
import InfoIcon from "@material-ui/icons/Info";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";

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

  const handleListItemClick = (event, model) => {
    if (selectedModel && selectedModel.id === model.id) setSelectedModel(null);
    else {
      setSelectedModel(model);
      handleClickModel(event, model);
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
    </div>
  );
};

const mapStateToProps = (state) => ({
  currentUser: state.user.user,
});

export default connect(mapStateToProps)(ModelList);
