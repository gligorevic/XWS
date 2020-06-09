import React, { useEffect } from "react";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import AddIcon from "@material-ui/icons/Add";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import EditIcon from "@material-ui/icons/Edit";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/Grid";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

const BrandList = ({
  allBrands,
  selectedBrand,
  setSelectedBrand,
  handleClickBrand,
  handleOpenDialog,
  handleOpenEditDialog,
  setEditText,
}) => {
  const classes = useStyles();

  const handleListItemClick = (event, brand) => {
    if (selectedBrand && brand.id === selectedBrand.id) setSelectedBrand(null);
    else {
      handleClickBrand(event, brand);
      setSelectedBrand(brand);
    }
  };

  return (
    <div className={classes.root}>
      <List
        component="nav"
        aria-label="main mailbox folders"
        style={{ height: 400, overflowY: "scroll" }}
      >
        {allBrands.map((brand) => {
          return (
            <>
              <Grid container>
                <Grid item md={10} className={classes.listContainer}>
                  <ListItem
                    button
                    selected={selectedBrand && brand.id === selectedBrand.id}
                    onClick={(event) => handleListItemClick(event, brand)}
                  >
                    <ListItemText primary={brand.brandName} />
                  </ListItem>
                </Grid>
                <Grid item md={2} className={classes.listContainer}>
                  <IconButton color="primary" aria-label="add to shopping cart">
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
        <ListItem button onClick={(e) => handleOpenDialog(e, 0)}>
          <ListItemIcon>
            <AddIcon />
          </ListItemIcon>
          <ListItemText primary="Add new brand" />
        </ListItem>
      </List>
    </div>
  );
};

export default BrandList;
