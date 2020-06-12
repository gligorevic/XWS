import React, { useEffect } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import { getAllRequests } from "../../../store/actions/request";

const useStyles = makeStyles({
  root: {
    maxWidth: 275,
    margin: 20,
  },
});

const RequestsPage = ({
  allRequests,
  getAllRequests,
  user,
  history,
  location,
}) => {
  useEffect(() => {
    getAllRequests(user.user.username);
  }, []);
  const classes = useStyles();

  return (
    <>
      <Grid container>
        {allRequests &&
          allRequests.map((requestInfo) => {
            return (
              <Grid item sm={12} md={4}>
                <Card className={classes.root}>
                  <CardContent>
                    <Typography variant="h5" component="h2">
                      {requestInfo.adId}
                    </Typography>

                    <Typography
                      variant="body2"
                      component="p"
                      style={{ marginTop: 20, color: "#222" }}
                    >
                      Pending requests: {requestInfo.pendingRequestNum}
                    </Typography>
                    <Typography
                      variant="body2"
                      component="p"
                      style={{ marginTop: 10, color: "#222" }}
                    >
                      Accepted requests: {requestInfo.acceptedRequestNum}
                    </Typography>

                    <Typography
                      variant="body2"
                      component="p"
                      style={{ marginTop: 10, color: "#222" }}
                    >
                      Canceled requests: {requestInfo.canceledRequestNum}
                    </Typography>
                  </CardContent>
                  <CardActions>
                    <Button
                      size="small"
                      variant="outlined"
                      color="primary"
                      onClick={() =>
                        location.pathname !==
                          `/request/ad/${requestInfo.adId}` &&
                        history.push(`/request/ad/${requestInfo.adId}`)
                      }
                    >
                      See Details
                    </Button>
                  </CardActions>
                </Card>
              </Grid>
            );
          })}
      </Grid>
    </>
  );
};

const mapStateToProps = (state) => ({
  user: state.user,
  allRequests: state.request.allRequests,
});

export default withRouter(
  connect(mapStateToProps, {
    getAllRequests,
  })(RequestsPage)
);
