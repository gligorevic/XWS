import React from "react";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import AddCarSelect from "../EndUser/Car/AddCarSelect";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Typography from "@material-ui/core/Typography";
import Slider from "@material-ui/core/Slider";

function valuetext(value) {
  return `${value}€`;
}

export default function AdvancedSearch({
  brand,
  setBrand,
  model,
  setModel,
  gearShift,
  setGearShif,
  fuelType,
  setFuelType,
  body,
  setBody,
  kmPassed,
  setKmPassed,
  childSeatsNum,
  setChildSeatsNum,
  collisionDamage,
  setCollisionDamage,
  kmRestriction,
  setKmRestriction,
  price,
  setPrice,
}) {
  return (
    <div>
      <Grid container spacing={3} justify="space-around" alignItems="center">
        <Grid item sm={12} md={4}>
          <AddCarSelect
            value={brand}
            setValue={setBrand}
            urlEndpoint={"/car-info/brand"}
            description="Brand"
            field="brandName"
            fetch
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <AddCarSelect
            value={model}
            setValue={setModel}
            urlEndpoint={`/car-info/brand/${brand && brand.brandName}/model`}
            description="Model"
            field="modelName"
            fetch={!!brand}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <AddCarSelect
            value={gearShift}
            setValue={setGearShif}
            urlEndpoint={`/car-info/model/${model && model.id}/gear-shift-type`}
            description="Gear Shift"
            field="gearShiftName"
            fetch={!!model}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <AddCarSelect
            value={fuelType}
            setValue={setFuelType}
            urlEndpoint={`/car-info/model/${model && model.id}/fuel-type`}
            description="Fuel Type"
            field="fuelTypeName"
            fetch={!!model}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <AddCarSelect
            value={body}
            setValue={setBody}
            urlEndpoint={`/car-info/model/${model && model.id}/body-type`}
            description="Body"
            field="bodyTypeName"
            fetch={!!model}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <TextField
            label="Max km passed"
            type="number"
            style={{ margin: "10px 8%" }}
            value={kmPassed}
            InputLabelProps={{
              shrink: true,
            }}
            onChange={(e) => setKmPassed(e.target.value)}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <TextField
            label="Km to pass"
            type="number"
            style={{ margin: "10px 8%" }}
            value={kmRestriction}
            InputLabelProps={{
              shrink: true,
            }}
            onChange={(e) => setKmRestriction(e.target.value)}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <TextField
            label="Number of child seats"
            type="number"
            style={{ margin: "10px 8%" }}
            value={childSeatsNum}
            InputLabelProps={{
              shrink: true,
            }}
            onChange={(e) => setChildSeatsNum(e.target.value)}
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <FormControlLabel
            control={
              <Checkbox
                checked={collisionDamage}
                onChange={(e) => setCollisionDamage(e.target.checked)}
                color="primary"
              />
            }
            label="Collision Damage"
          />
        </Grid>
        <Grid item sm={12} md={4}>
          <Typography
            id="range-slider"
            gutterBottom
            style={{ textAlign: "center" }}
          >
            Price per day
          </Typography>
          <Slider
            style={{ opacity: 0.9 }}
            value={price}
            onChange={(e, newVal) => setPrice(newVal)}
            valueLabelDisplay="auto"
            aria-labelledby="range-slider"
            max={150}
            valueLabelDisplay="on"
            getAriaValueText={valuetext}
            valueLabelFormat={valuetext}
          />
        </Grid>
      </Grid>
    </div>
  );
}
