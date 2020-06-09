import React, { useEffect, useState } from "react";
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
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Axios from "axios";

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
  setAllBrands,
}) => {
  const classes = useStyles();

  const handleListItemClick = (event, brand) => {
    if (selectedBrand && brand.id === selectedBrand.id) setSelectedBrand(null);
    else {
      handleClickBrand(event, brand);
      setSelectedBrand(brand);
    }
  };
  const [editDialogOpened, setEditDialogOpened] = useState(false);

  const [text, setText] = useState("");
  const [brand, setBrand] = useState(null);

  const handleClose = (e) => {
    setEditDialogOpened(false);
    setBrand(null);
  };

  const handleOpenEditDialog = (e, entity) => {
    e.stopPropagation();
    setEditDialogOpened(true);
    setText(entity.brandName);
    setBrand(entity);
  };

  const handleChange = (e) => {
    setText(e.target.value);
  };

  const handleSubmitEdit = async () => {
    setEditDialogOpened(false);
    const response = await Axios.put(`car-info/brand/${brand.id}`, text, {
      headers: { "Content-Type": "text/plain" },
    });

    if (response.status >= 200 && response.status < 300) {
      setAllBrands((oldBrandList) =>
        oldBrandList.map((brandItem) =>
          brandItem.id === brand.id ? response.data : brandItem
        )
      );
      setBrand(null);
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
            <ListItem
              button
              selected={selectedBrand && brand.id === selectedBrand.id}
              onClick={(event) => handleListItemClick(event, brand)}
            >
              <ListItemText primary={brand.brandName} />
              <IconButton
                style={{ float: "right" }}
                onClick={(e) => handleOpenEditDialog(e, brand)}
                color="primary"
                aria-label="add to shopping cart"
              >
                <EditIcon />
              </IconButton>
            </ListItem>
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
      <Dialog
        open={editDialogOpened}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle>Edit brand</DialogTitle>
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

export default BrandList;
