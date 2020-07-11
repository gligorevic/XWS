import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { getPricelists } from "../../../store/actions/pricelist";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import IconButton from "@material-ui/core/IconButton";
import AddIcon from "@material-ui/icons/Add";
import Tooltip from "@material-ui/core/ToolTip";
import "./AllUserPricelists.css";
import axios from "axios";
import PricelistCard from "./PricelistCard";

const AllUserPricelists = ({
  getPricelists,
  pricelists,
  setAddPricelistDialogOpened,
  userEmail,
}) => {
  const [open, setOpened] = useState(false);

  useEffect(() => {
    getPricelists();
  }, []);

  return (
    <div style={{ overflow: "hidden", minHeight: "100vh" }}>
      <Typography variant="h5">My pricelists</Typography>
      <Divider style={{ marginBottom: 40 }} />
      <Grid container spacing={3}>
        {pricelists.length > 0 &&
          pricelists.map((pricelist) => (
            <Grid item xs={12} sm={6} md={3} key={pricelist.id}>
              <PricelistCard pricelist={pricelist} userEmail={userEmail} />
            </Grid>
          ))}
        <Grid item xs={12} sm={6} md={3}>
          <Card
            className={open ? "scaleOnClick" : ""}
            style={{
              opacity: 0.7,
              backgroundColor: "#f5f5f5",
              boxShadow: "0px 2px 12px 0px #4caf5070",
            }}
          >
            <CardContent>
              <Tooltip style={{ color: "#4caf50" }} title="Add new pricelist">
                <IconButton
                  onClick={() => {
                    setOpened(true);
                    setTimeout(function () {
                      setAddPricelistDialogOpened(true);
                      setTimeout(() => setOpened(false), 140);
                    }, 400);
                  }}
                >
                  <AddIcon fontSize="large" />
                </IconButton>
              </Tooltip>
            </CardContent>
            <CardMedia className="media"></CardMedia>
          </Card>
        </Grid>
      </Grid>
    </div>
  );
};

const mapStateToProps = (state) => ({
  pricelists: state.pricelist.pricelists,
  userEmail: state.user.user.username,
});

export default connect(mapStateToProps, { getPricelists })(AllUserPricelists);
