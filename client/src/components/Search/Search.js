import React, { useState } from "react";
import Paper from "@material-ui/core/Paper";
import CitySearch from "./CitySearch";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Collapse from "@material-ui/core/Collapse";
import Button from "@material-ui/core/Button";
import clsx from "clsx";
import IconButton from "@material-ui/core/IconButton";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import DateInputForm from "./DateInputForm";
import { searchAdvertisements } from "../../store/actions/advertisement";
import { connect } from "react-redux";
import AdvancedSearch from "./AdvancedSearch";

const useStyles = makeStyles((theme) => ({
  root: {
    margin: "60px auto",
    marginBottom: 30,
    padding: "30px 30px",
    maxWidth: 1000,
    boxShadow: "0px 2px 23px 4px rgba(0,0,0,0.75)",
    [theme.breakpoints.down("md")]: {
      maxWidth: 400,
    },
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
  center: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
}));

const Search = ({
  searchAdvertisements,
  selectedStartDate,
  setSelectedStartDate,
  selectedEndDate,
  setSelectedEndDate,
}) => {
  const classes = useStyles();
  const [error, setError] = useState(false);

  const [cityName, setCityName] = useState("");

  const [advancedSearchOpen, setAdvancedSearchOpen] = useState(false);

  const [brand, setBrand] = useState();
  const [model, setModel] = useState();
  const [gearShift, setGearShif] = useState();
  const [fuelType, setFuelType] = useState();
  const [body, setBody] = useState();
  const [kmPassed, setKmPassed] = useState();
  const [childSeatsNum, setChildSeatsNum] = useState();
  const [collisionDamage, setCollisionDamage] = useState();
  const [kmRestriction, setKmRestriction] = useState();
  const [price, setPrice] = React.useState([0, 150]);

  const handleExpandClick = (e) => {
    setAdvancedSearchOpen(!advancedSearchOpen);
  };

  const handleSubmit = async (e) => {
    const resp = await searchAdvertisements({
      cityName: cityName.length > 0 ? cityName : null,
      brandName: brand?.brandName,
      modelName: model?.modelName,
      gearShiftName: gearShift?.gearShiftName,
      bodyName: body?.bodyTypeName,
      fuelTypeName: fuelType?.fuelTypeName,
      freeFrom: selectedStartDate,
      freeTo: selectedEndDate,
      kmPassed,
      numberChildSeats: childSeatsNum,
      collisionDamage,
      kmRestriction,
      price: price[0],
      priceTo: price[1],
    });
    if (resp.status === 400) {
      setError(true);
    }
  };

  return (
    <Paper className={classes.root}>
      <Grid container spacing={2}>
        <Grid item sm={12}>
          <h2 style={{ margin: "0px 10px" }}>Search</h2>
        </Grid>
        <Grid item sm={12} lg={4} className={classes.center}>
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
            minDate={selectedStartDate}
          />
        </Grid>
        <Grid item sm={12} style={{ paddingTop: 0 }}>
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
          <h3 style={{ display: "inline-block" }}>
            <em>Advanced search</em>
          </h3>
          <Collapse in={advancedSearchOpen} timeout="auto" unmountOnExit>
            <AdvancedSearch
              brand={brand}
              setBrand={setBrand}
              model={model}
              setModel={setModel}
              gearShift={gearShift}
              setGearShif={setGearShif}
              fuelType={fuelType}
              setFuelType={setFuelType}
              body={body}
              setBody={setBody}
              kmPassed={kmPassed}
              setKmPassed={setKmPassed}
              childSeatsNum={childSeatsNum}
              setChildSeatsNum={setChildSeatsNum}
              collisionDamage={collisionDamage}
              setCollisionDamage={setCollisionDamage}
              kmRestriction={kmRestriction}
              setKmRestriction={setKmRestriction}
              price={price}
              setPrice={setPrice}
            />
          </Collapse>
        </Grid>
        <Grid
          item
          sm={12}
          className={classes.floatRight}
          style={{ paddingTop: 0 }}
        >
          <Button
            variant="contained"
            color="primary"
            onClick={handleSubmit}
            disabled={selectedStartDate > selectedEndDate}
          >
            Search
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default connect(null, { searchAdvertisements })(Search);
