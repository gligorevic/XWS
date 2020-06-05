import React, { useState } from "react";
import Paper from "@material-ui/core/Paper";
import CitySearch from "./CitySearch";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Collapse from "@material-ui/core/Collapse";
import Button from "@material-ui/core/Button";
import clsx from "clsx";
import IconButton from "@material-ui/core/IconButton";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import DateInputForm from "./DateInputForm";
import { searchAdvertisements } from "../../store/actions/advertisement";
import { connect } from "react-redux";

const useStyles = makeStyles((theme) => ({
  root: {
    margin: "60px auto",
    padding: "30px 30px",
    maxWidth: 950,
  },
  expand: {
    transform: "rotate(0deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: "rotate(180deg)",
  },
  floatRight: {
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
  },
}));

const Search = ({ searchAdvertisements }) => {
  const classes = useStyles();
  const twoDaysFromNow = new Date(
    new Date().getTime() + 1000 * 60 * 60 * 24 * 2
  );

  const [error, setError] = useState(false);

  const [cityName, setCityName] = useState("");
  const [selectedStartDate, setSelectedStartDate] = useState(twoDaysFromNow);
  const [selectedEndDate, setSelectedEndDate] = useState(twoDaysFromNow);

  const [advancedSearchOpen, setAdvancedSearchOpen] = useState(false);

  const handleExpandClick = (e) => {
    setAdvancedSearchOpen(!advancedSearchOpen);
  };

  const handleSubmit = async (e) => {
    console.log(cityName);
    const resp = await searchAdvertisements({
      cityName: cityName.length > 0 ? cityName : null,
      freeFrom: new Date(selectedStartDate.getTime() + 1000 * 60 * 60 * 24),
      freeTo: new Date(selectedEndDate.getTime() + 1000 * 60 * 60 * 24),
    });
    if (resp.status === 400) {
      setError(true);
    }
  };

  return (
    <Paper className={classes.root}>
      <Grid container spacing={2}>
        <Grid item sm={12} lg={4}>
          <CitySearch
            cityName={cityName}
            setCityName={setCityName}
            error={error}
            setError={setError}
          />
        </Grid>
        <Grid item sm={12} lg={4}>
          <DateInputForm
            selectedDate={selectedStartDate}
            setSelectedDate={setSelectedStartDate}
          />
        </Grid>
        <Grid item sm={12} lg={4}>
          <DateInputForm
            selectedDate={selectedEndDate}
            setSelectedDate={setSelectedEndDate}
          />
        </Grid>
        <Grid item sm={12}>
          <IconButton
            className={clsx(classes.expand, {
              [classes.expandOpen]: advancedSearchOpen,
            })}
            onClick={handleExpandClick}
            aria-expanded={advancedSearchOpen}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </IconButton>
          <Collapse in={advancedSearchOpen} timeout="auto" unmountOnExit>
            <Typography paragraph>ADVANCED SEARCH</Typography>
          </Collapse>
        </Grid>
        <Grid item sm={12} className={classes.floatRight}>
          <Button variant="contained" color="primary" onClick={handleSubmit}>
            Search
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default connect(null, { searchAdvertisements })(Search);
