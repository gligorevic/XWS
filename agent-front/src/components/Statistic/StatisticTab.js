import React, { useState, useEffect } from "react";
import { withRouter } from "react-router";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import clsx from "clsx";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import { connect } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import { getStatistic } from "../../store/actions/statistic";
import AverageGrade from "./AverageGrade";
import StatisticList from "./StatisticList";
import Chart from "./Chart";

const useStyles = makeStyles((theme) => ({
  content: {
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
  container: {
    paddingBottom: theme.spacing(2),
  },
  paper: {
    padding: theme.spacing(1),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
  fixedHeight: {
    height: 240,
  },
}));

function StatisticTab({ statistic, getStatistic }) {
  useEffect(() => {
    getStatistic();
  }, []);
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  return (
    <main className={classes.content}>
      <Typography variant="h5">Statistic</Typography>
      <Divider style={{ marginBottom: 40 }} />
      <div />
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={12} md={8} lg={9}>
            <Paper className={fixedHeightPaper}>
              {statistic && <Chart statistic={statistic} />}
            </Paper>
          </Grid>
          <Grid item xs={12} md={4} lg={3}>
            <Paper className={fixedHeightPaper}>
              {statistic && <AverageGrade statistic={statistic} />}
            </Paper>
          </Grid>
          <Grid item xs={12}>
            <Paper className={classes.paper}>
              {statistic && <StatisticList statistic={statistic} />}
            </Paper>
          </Grid>
        </Grid>
        <Box pt={4}></Box>
      </Container>
    </main>
  );
}

const mapStateToProps = (state) => ({
  statistic: state.statistic.statistic,
});

export default withRouter(
  connect(mapStateToProps, { getStatistic })(StatisticTab)
);
