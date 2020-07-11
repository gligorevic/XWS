import React, { useEffect } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import InfoIcon from "@material-ui/icons/Info";
import { getAllRequests } from "../../store/actions/request";

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

  return (
    <>
      {allRequests.length === 0 ? (
        <div
          style={{
            minHeight: 400,
            display: "flex",
            margin: "10% 30%",
            color: "#777",
          }}
        >
          <InfoIcon />
          Your advertisements don't have any requests yet.
        </div>
      ) : (
        <div>
          <Grid container spacing={3}>
            {allRequests.map((requestInfo) => {
              return (
                <Grid item style={{ width: 250 }}>
                  <Card>
                    <CardContent>
                      <Typography variant="h5" component="h2">
                        {requestInfo.brandName} - {requestInfo.modelName}
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
        </div>
      )}
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
