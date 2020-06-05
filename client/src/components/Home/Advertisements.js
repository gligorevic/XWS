import React, { useEffect } from "react";
import { getAllAdvertisements } from "../../store/actions/advertisement";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import { connect } from "react-redux";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import CardHeader from "@material-ui/core/CardHeader";
import CardMedia from "@material-ui/core/CardMedia";
import LocalOfferIcon from "@material-ui/icons/LocalOffer";
import "./Avertisements.css";

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 1000,
  },
  card: {
    boxShadow: "0px 2px 23px 2px rgba(0,0,0,0.75)",
  },
}));

const Advertisements = ({ ads, getAllAdvertisements }) => {
  const classes = useStyles();

  useEffect(() => {
    getAllAdvertisements();
  }, []);

  return (
    <Grid
      container
      spacing={3}
      justify="space-between"
      alignItems="center"
      className={classes.root}
    >
      {ads &&
        ads.map((row) => {
          return (
            <Grid item sm={12} md={4}>
              <Card className={classes.card}>
                <CardHeader
                  action={
                    <IconButton aria-label="settings" className="priceToggler">
                      <LocalOfferIcon className="priceButton" />
                      <span className="purePrice">{row.priceFrom}</span>
                    </IconButton>
                  }
                  title={`${row.brandName} - ${row.modelName}`}
                  subheader="September 14, 2016"
                />
                <CardMedia
                  className={classes.media}
                  image="/static/images/cards/paella.jpg"
                  title="Paella dish"
                />
                <CardContent></CardContent>
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
