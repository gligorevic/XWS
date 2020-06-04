import React, { useEffect } from "react";
import MainNavbar from "../layouts/Navbar/MainNavbar";
import ficaImage from "../../images/fica.jpg";
import { getAllAdvertisements } from "../../store/actions/advertisement";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Paper from "@material-ui/core/Paper";
import AdvertisementDetails from "../Dialogs/AdvertisementDetails";

const useStyles = makeStyles({
  root: {
    maxWidth: 345,
    minWidth: 345,
  },
  media: {
    height: 140,
  },
  grid: {
    padding: 20,
    flexGrow: 1,
  },
  back: {
    background: "url(" + ficaImage + ")",
  },
  paper: {
    marginTop: 10,
  },
});

const Home = ({ ads, getAllAdvertisements }) => {
  useEffect(() => {
    getAllAdvertisements();
  }, []);
  const classes = useStyles();
  return (
    <div className={classes.back}>
      <MainNavbar />
      <Paper className={classes.paper} fullWidth></Paper>
      <Grid container>
        {ads &&
          ads.map((row) => {
            return (
              <Grid item xs className={classes.grid} spacing={4}>
                <Card className={classes.root}>
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
                          <ListItem>
                            {"Price per day: " + row.priceFrom}
                          </ListItem>
                          <ListItem>
                            {"Fuel type: " + row.fuelTypeName}
                          </ListItem>
                          <ListItem>
                            {"Gear shift: " + row.gearShiftName}
                          </ListItem>
                        </List>
                      </Typography>
                    </CardContent>
                  </CardActionArea>
                  <CardActions>
                    <AdvertisementDetails id={row.id} />
                    <IconButton
                      color="primary"
                      aria-label="add to shopping cart"
                    >
                      <AddShoppingCartIcon />
                    </IconButton>
                  </CardActions>
                </Card>
              </Grid>
            );
          })}
      </Grid>
    </div>
  );
};

const mapStateToProps = (state) => ({
  ads: state.advertisement.allAdvertisements,
});

export default withRouter(
  connect(mapStateToProps, { getAllAdvertisements })(Home)
);
