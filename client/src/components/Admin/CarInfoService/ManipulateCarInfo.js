import React, { useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";

import BrandList from "./BrandList";
import ModelList from "./ModelList";
import TypesList from "./TypesList";
import AddNewTypeForm from "./AddNewTypeForm";

import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Axios from "axios";
import { Button } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
      width: "25ch",
    },
  },
}));

export default function ManipulateCarInfo() {
  const classes = useStyles();

  const [selectedValueType, setSelectedValueType] = React.useState("");

  const [selectedBrand, setSelectedBrand] = React.useState(null);
  const [selectedModel, setSelectedModel] = React.useState(null);

  const [availableTypeList, setAvailableTypeList] = React.useState([]);

  const [stateType, setStateType] = React.useState("");
  const [stateModel, setStateModel] = React.useState({
    modelName: "",
    brandName: "",
  });
  const [stateBrand, setStateBrand] = React.useState("");

  const [addNewType, setAddNewType] = React.useState(null);
  const [addNewModel, setAddNewModel] = React.useState(false);
  const [addNewBrand, setAddNewBrand] = React.useState(false);

  const [allModels, setAllModels] = React.useState([]);
  const [allFuelTypes, setAllFuelTypes] = React.useState([]);
  const [allGearShiftTypes, setAllGearShiftTypes] = React.useState([]);
  const [allBodyTypes, setAllBodyTypes] = React.useState([]);
  const [allBrands, setAllBrands] = React.useState([]);

  useEffect(() => {
    (async () => {
      const res = await Axios.get("/car-info/brand");
      setAllBrands(res.data);
    })();
  }, []);

  const handleClickBrand = async (event, brand) => {
    if (brand !== "newBrand") {
      const res = await Axios.get(`/car-info/brand/${brand.brandName}/model`);
      setAllModels(res.data);
      console.log(allModels);
      brand.models = res.data;
      setSelectedBrand(brand);
    } else {
      setSelectedBrand(null);
      setAddNewBrand(true);
    }
  };

  const handleClickModel = async (event, model) => {
    if (model !== "newModel") {
      const fuelType = await Axios.get(
        `/car-info/model/${model.modelName}/fuel-type`
      );
      const gearShiftType = await Axios.get(
        `/car-info/model/${model.modelName}/gear-shift-type`
      );
      const bodyType = await Axios.get(
        `/car-info/model/${model.modelName}/body-type`
      );
      setAllBodyTypes(bodyType.data);
      setAllFuelTypes(fuelType.data);
      setAllGearShiftTypes(gearShiftType.data);
      setSelectedModel(model);
    } else {
      setSelectedModel(null);
      setAddNewModel(true);
    }
  };

  const handleClickType = async (event, type) => {
    var getAvailableTypeList = [];
    switch (type) {
      case "newFuelType":
        setAddNewType("fuel");
        getAvailableTypeList = await Axios.get(`/car-info/fuel-type`);
        setAvailableTypeList(getAvailableTypeList.data);
        return "";

      case "newGearShiftType":
        setAddNewType("gear");
        getAvailableTypeList = await Axios.get(`/car-info/gear-shift-type`);
        setAvailableTypeList(getAvailableTypeList.data);
        return "";

      case "newBodyType":
        setAddNewType("body");
        getAvailableTypeList = await Axios.get(`/car-info/body-type`);
        setAvailableTypeList(getAvailableTypeList.data);
        return "";
      default:
        return "";
    }
  };

  const handleSubmitModel = async (e) => {
    try {
      e.preventDefault();
      stateModel.brandName = selectedBrand.brandName;
      const newModelType = await Axios.post("/car-info/model", stateModel);
      setAddNewModel(false);
    } catch (e) {
      console.log(e);
    }
  };

  const handleSubmitBrand = async (e) => {
    try {
      e.preventDefault();
      const newBrandType = await Axios.post(
        `/car-info/brand/${stateBrand.brandName}`
      );
      setAddNewBrand(false);
    } catch (e) {
      console.log(e);
    }
  };

  const handleChangeModel = (e) => {
    setStateModel({ ...stateModel, [e.target.name]: e.target.value });
  };

  const handleChangeBrand = (e) => {
    setStateBrand({ ...stateBrand, [e.target.name]: e.target.value });
  };

  return (
    <Grid container spacing={3}>
      <Grid item sm={12} lg={5}>
        {addNewBrand === false && (
          <>
            <h2 style={{ textAlign: "center" }}>Brands List</h2>
            <BrandList
              handleClickBrand={handleClickBrand}
              setSelectedBrand={setSelectedBrand}
            />
          </>
        )}
        {addNewBrand === true && (
          <>
            <h2 style={{ textAlign: "center" }}>Add new brand</h2>
            <form
              className={classes.root}
              noValidate
              autoComplete="off"
              onSubmit={handleSubmitBrand}
            >
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="brandName"
                label="Brand name"
                id="brandName"
                onChange={handleChangeBrand}
              />
              <br />
              <Button type="submit" variant="contained" color="primary">
                Add new brand
              </Button>
            </form>
          </>
        )}
      </Grid>

      <Grid item sm={12} lg={5}>
        {addNewModel === false && (
          <>
            <h2 style={{ textAlign: "center" }}>Car Model List</h2>
            <ModelList
              selectedModel={selectedModel}
              allModels={allModels}
              handleClickModel={handleClickModel}
              setSelectedModel={setSelectedModel}
            />
          </>
        )}
        {addNewModel === true && (
          <>
            <h2 style={{ textAlign: "center" }}>Add new model</h2>
            <form
              className={classes.root}
              noValidate
              autoComplete="off"
              onSubmit={handleSubmitModel}
            >
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="modelName"
                label="Model name"
                id="modelName"
                onChange={handleChangeModel}
              />
              <br />
              <Button
                disabled={selectedBrand === null}
                type="submit"
                variant="contained"
                color="primary"
              >
                Add new model
              </Button>
            </form>
          </>
        )}
      </Grid>

      <Grid item textAlign="center">
        {addNewType === null && (
          <Paper>
            <h2 style={{ textAlign: "center" }}>Types</h2>
            <TypesList
              allBodyTypes={allBodyTypes}
              allFuelTypes={allFuelTypes}
              allGearShiftTypes={allGearShiftTypes}
              handleClickType={handleClickType}
            />
          </Paper>
        )}
        {addNewType !== null && (
          <Paper>
            <AddNewTypeForm
              setAddNewType={setAddNewType}
              addNewType={addNewType}
              allBodyTypes={allBodyTypes}
              allFuelTypes={allFuelTypes}
              allGearShiftTypes={allGearShiftTypes}
              availableTypeList={availableTypeList}
              setAvailableTypeList={setAvailableTypeList}
              selectedModel={selectedModel}
            ></AddNewTypeForm>
          </Paper>
        )}
      </Grid>
    </Grid>
  );
}
