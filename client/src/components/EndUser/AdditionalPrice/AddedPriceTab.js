import React, { useEffect } from "react";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Divider from "@material-ui/core/Divider";
import { getAddedPrices } from "../../../store/actions/pricelist";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import { withRouter } from "react-router";
import { connect } from "react-redux";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  content: {
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
  container: {
    paddingBottom: theme.spacing(2),
  },
}));

const AddedPriceTab = ({ prices, getAddedPrices }) => {
  useEffect(() => {
    getAddedPrices();
  }, []);
  const classes = useStyles();

  const handlePay = async (id) => {
    const resp = await axios.put(`/price-list/added-price/${id}`, id);
    if (resp.status === 200) {
      getAddedPrices();
    }
  };

  return (
    <>
      <main className={classes.content}>
        <Typography variant="h5">Additional expences</Typography>
        <Divider style={{ marginBottom: 40 }} />
        <Container maxWidth="lg" className={classes.container}>
          <List>
            {prices && prices.length == 0 && (
              <Typography variant="h5">
                You don't have any additional expences.
              </Typography>
            )}
            {prices &&
              prices.map((price) => {
                return (
                  <ListItem>
                    <Grid container justify="space-around">
                      <Grid item sm={2}>
                        <ListItemText primary={price.price} />
                      </Grid>
                      <Grid
                        item
                        style={{
                          display: "flex",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                        sm={1}
                      >
                        <Divider orientation="vertical" />
                      </Grid>
                      <Grid item sm={2}>
                        <ListItemText primary={price.userEmail} />
                      </Grid>
                      <Grid
                        item
                        style={{
                          display: "flex",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                        sm={1}
                      >
                        <Divider orientation="vertical" />
                      </Grid>
                      <Grid
                        item
                        sm={6}
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                        }}
                      >
                        <Button
                          color="primary"
                          size="small"
                          variant="contained"
                          onClick={() => {
                            handlePay(price.id);
                          }}
                        >
                          Pay
                        </Button>
                      </Grid>
                    </Grid>
                  </ListItem>
                );
              })}
          </List>
        </Container>
      </main>
    </>
  );
};

const mapStateToProps = (state) => ({
  prices: state.pricelist.addedPrices,
});

export default withRouter(
  connect(mapStateToProps, { getAddedPrices })(AddedPriceTab)
);
