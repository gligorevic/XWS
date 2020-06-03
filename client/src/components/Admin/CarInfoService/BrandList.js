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

import { getAllBrands } from "../../../store/actions/carInfo";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

const BrandList = ({
  getAllBrands,
  allBrands,
  setSelectedBrand,
  handleClickBrand,
}) => {
  const classes = useStyles();
  const [selectedIndex, setSelectedIndex] = React.useState(null);

  useEffect(() => {
    (async () => {
      await getAllBrands();
    })();
  }, []);

  const handleListItemClick = (event, brand, index) => {
    console.log(index);
    setSelectedIndex(index);
    setSelectedBrand(brand);
    handleClickBrand(event, brand);
  };

  return (
    <div className={classes.root}>
      {allBrands && (
        <List component="nav" aria-label="main mailbox folders">
          {allBrands.map((brand, index) => {
            return (
              <ListItem
                button
                selected={selectedIndex === index}
                onClick={(event) => handleListItemClick(event, brand, index)}
              >
                <ListItemText primary={brand.brandName} />
              </ListItem>
            );
          })}
          <Divider />

          <ListItem
            button
            selected={selectedIndex === allBrands.length}
            onClick={(event) =>
              handleListItemClick(event, "newBrand", allBrands.length)
            }
          >
            <ListItemIcon>
              <AddIcon />
            </ListItemIcon>
            <ListItemText primary="Add new brand" />
          </ListItem>
        </List>
      )}
    </div>
  );
};

const mapStateToProps = (state) => ({
  allBrands: state.carInfo.allBrands,
  currentUser: state.user.user,
});

export default connect(mapStateToProps, {
  getAllBrands,
})(BrandList);
