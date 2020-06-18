import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import { withRouter } from "react-router";
import { connect } from "react-redux";
import { format } from "date-fns";
import Rating from "@material-ui/lab/Rating";

const useStyles = makeStyles({
  depositContext: {
    flex: 1,
  },
});

const AverageGrade = ({ statistic }) => {
  const classes = useStyles();
  return (
    <React.Fragment>
      <Typography component="h2" variant="h6" color="secondary" gutterBottom>
        Overall average grade
      </Typography>
      <Typography component="p" variant="h4">
        {statistic.averageGradeCars}
      </Typography>
      <Typography color="textSecondary" className={classes.depositContext}>
        {`on ` + format(new Date(), "dd MMM yyyy")}
      </Typography>
      <div>
        <Rating
          name="read-only"
          value={statistic.averageGradeCars}
          precision={0.25}
          readOnly
        />
      </div>
    </React.Fragment>
  );
};

const mapStateToProps = (state) => ({});

export default withRouter(connect(mapStateToProps, {})(AverageGrade));
