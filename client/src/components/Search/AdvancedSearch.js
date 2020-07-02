import React from "react";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import AddCarSelect from "../EndUser/Car/AddCarSelect";

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
      </Grid>
    </div>
  );
}
