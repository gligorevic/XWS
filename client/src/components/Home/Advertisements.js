import React, { useEffect } from "react";
import { getAllAdvertisements } from "../../store/actions/advertisement";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

import { connect } from "react-redux";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";

const Advertisements = ({ ads, getAllAdvertisements }) => {
  useEffect(() => {
    getAllAdvertisements();
  }, []);

  return (
    <Grid container>
      {ads &&
        ads.map((row) => {
          return (
            <Grid item xs spacing={4}>
              <Card>
                <CardActionArea>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      {row.brandName + " - " + row.modelName}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="textSecondary"
                      component="p"
                    >
                      <List>
                        <ListItem>
                          {"Renting location: " + row.rentingLocation}
                        </ListItem>
                        <ListItem>{"Price per day: " + row.priceFrom}</ListItem>
                        <ListItem>{"Fuel type: " + row.fuelTypeName}</ListItem>
                        <ListItem>
                          {"Gear shift: " + row.gearShiftName}
                        </ListItem>
                      </List>
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardActions>
                  <Button size="small" color="primary">
                    View more
                  </Button>
                  <IconButton color="primary" aria-label="add to shopping cart">
                    <AddShoppingCartIcon />
                  </IconButton>
                </CardActions>
              </Card>
            </Grid>
          );
        })}
    </Grid>
  );
};

const mapStateToProps = (state) => ({
  ads: state.advertisement.allAdvertisements,
});

export default connect(mapStateToProps, { getAllAdvertisements })(
  Advertisements
);
