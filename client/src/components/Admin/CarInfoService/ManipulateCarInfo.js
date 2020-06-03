import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";

import BrandList from "./BrandList";
import ModelList from "./ModelList";
import TypesList from "./TypesList";

import Axios from "axios";

import AddNewDialog from "./AddNewDialog";
import AddNewTypeDialog from "./AddNewTypeDialog";

const dialogEnum = {
  UNOPEND: -1,
  NEWBRAND: 0,
  NEWMODEL: 1,
  NEWFUELTYPE: 2,
  NEWGEARSHIFTTYPE: 3,
  NEWBODYTYPE: 4,
};

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
      width: "25ch",
    },
  },
  listContainer: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "column",
  },
}));

export default function ManipulateCarInfo() {
  const classes = useStyles();

  const [selectedBrand, setSelectedBrand] = useState(null);
  const [selectedModel, setSelectedModel] = useState(null);

  const [availableTypeList, setAvailableTypeList] = useState([]);

  const [newEntityData, setNewEntityData] = useState("");

  const [addDialogOpened, setAddDialogOpened] = useState(dialogEnum.UNOPEND);
  const [allModels, setAllModels] = useState([]);
  const [allFuelTypes, setAllFuelTypes] = useState([]);
  const [allGearShiftTypes, setAllGearShiftTypes] = useState([]);
  const [allBodyTypes, setAllBodyTypes] = useState([]);
  const [allBrands, setAllBrands] = useState([]);

  useEffect(() => {
    (async () => {
      const res = await Axios.get("/car-info/brand");
      setAllBrands(res.data);
    })();
  }, []);

  const handleClickBrand = async (event, brand) => {
    const res = await Axios.get(`/car-info/brand/${brand.brandName}/model`);
    setAllModels(res.data);

    brand.models = res.data;
  };

  const handleClickModel = async (e, model) => {
    const fuelType = await Axios.get(`/car-info/model/${model.id}/fuel-type`);
    const gearShiftType = await Axios.get(
      `/car-info/model/${model.id}/gear-shift-type`
    );
    const bodyType = await Axios.get(`/car-info/model/${model.id}/body-type`);

    setAllBodyTypes(bodyType.data);
    setAllFuelTypes(fuelType.data);
    setAllGearShiftTypes(gearShiftType.data);

    setSelectedModel(model);
    setAddDialogOpened(dialogEnum.UNOPEND);
  };

  const handleOpenDialog = (e, dialogType) => {
    setAddDialogOpened(dialogType);
  };

  const handleClickType = async (event, type) => {
    let getAvailableTypeList;
    switch (type) {
      case "newFuelType":
        getAvailableTypeList = await Axios.get(`/car-info/fuel-type`);
        setAvailableTypeList(
          getAvailableTypeList.data.filter((availableType) =>
            allFuelTypes.every(
              (ownedFuelType) => availableType.id !== ownedFuelType.id
            )
          )
        );
        setAddDialogOpened(dialogEnum.NEWFUELTYPE);
        break;

      case "newGearShiftType":
        getAvailableTypeList = await Axios.get(`/car-info/gear-shift-type`);
        setAvailableTypeList(
          getAvailableTypeList.data.filter((availableType) =>
            allGearShiftTypes.every(
              (ownedGearShift) => ownedGearShift.id !== availableType.id
            )
          )
        );
        setAddDialogOpened(dialogEnum.NEWGEARSHIFTTYPE);
        break;

      case "newBodyType":
        getAvailableTypeList = await Axios.get(`/car-info/body-type`);
        setAvailableTypeList(
          getAvailableTypeList.data.filter((availableType) =>
            allBodyTypes.every(
              (ownedBodyType) => ownedBodyType.id !== availableType.id
            )
          )
        );
        setAddDialogOpened(dialogEnum.NEWBODYTYPE);
        break;
    }
  };

  const handleSubmitModel = async (e) => {
    try {
      e.preventDefault();
      const newModel = {
        modelName: newEntityData,
        brandName: selectedBrand.brandName,
      };
      await Axios.post("/car-info/model", newModel);
      setAllModels((oldState) => [...oldState, newModel]);
      setAddDialogOpened(dialogEnum.UNOPEND);
    } catch (e) {
      console.log(e);
    }
  };

  const handleSubmitBrand = async (e) => {
    try {
      e.preventDefault();
      const response = await Axios.post(`/car-info/brand`, newEntityData, {
        headers: { "Content-Type": "text/plain" },
      });
      console.log(allBrands);
      console.log(response.data);
      setAllBrands((oldBrands) => [...oldBrands, response.data]);
      setAddDialogOpened(dialogEnum.UNOPEND);
    } catch (e) {
      console.log(e);
    }
  };

  const handleClose = (e) => {
    setAddDialogOpened(dialogEnum.UNOPEND);
  };

  const handleChange = (e) => {
    setNewEntityData(e.target.value);
  };

  const addingDialog = () => {
    let availableTypes;

    switch (addDialogOpened) {
      case dialogEnum.NEWBRAND:
        return (
          <AddNewDialog
            helperText="Brand name"
            title="Add new brand"
            handleSubmit={handleSubmitBrand}
            handleClose={handleClose}
            handleChange={handleChange}
            open={dialogEnum.NEWBRAND === addDialogOpened}
          />
        );
      case dialogEnum.NEWMODEL:
        return (
          <AddNewDialog
            helperText="Model name"
            title="Add new model"
            handleSubmit={handleSubmitModel}
            handleClose={handleClose}
            handleChange={handleChange}
            open={dialogEnum.NEWMODEL === addDialogOpened}
          />
        );
      case dialogEnum.NEWFUELTYPE:
      case dialogEnum.NEWGEARSHIFTTYPE:
      case dialogEnum.NEWBODYTYPE:
        return (
          <AddNewTypeDialog
            handleClose={handleClose}
            setAddDialogOpened={setAddDialogOpened}
            selectedModel={selectedModel}
            open={addDialogOpened >= dialogEnum.NEWFUELTYPE}
            currentDialog={addDialogOpened}
            availableTypes={availableTypeList}
            setAllFuelTypes={setAllFuelTypes}
            setAllGearShiftTypes={setAllGearShiftTypes}
            setAllBodyTypes={setAllBodyTypes}
            isModelSelected={selectedModel !== null}
          />
        );
    }
  };

  return (
    <>
      <Grid container spacing={2}>
        <Grid item sm={12} xl={7}>
          <Grid container spacing={1}>
            <Grid item sm={12} lg={6} className={classes.listContainer}>
              <h2>Brands List</h2>
              <BrandList
                allBrands={allBrands}
                selectedBrand={selectedBrand}
                handleClickBrand={handleClickBrand}
                setSelectedBrand={setSelectedBrand}
                handleOpenDialog={handleOpenDialog}
              />
            </Grid>
            <Grid item sm={12} lg={6} className={classes.listContainer}>
              <h2 style={{ textAlign: "center" }}>Car Model List</h2>
              <ModelList
                isSelectedBrand={selectedBrand !== null}
                selectedModel={selectedModel}
                allModels={allModels}
                handleClickModel={handleClickModel}
                setSelectedModel={setSelectedModel}
                handleOpenDialog={handleOpenDialog}
              />
            </Grid>
          </Grid>
        </Grid>

        <Grid item sm={12} xl={4} className={classes.listContainer}>
          <h2 style={{ textAlign: "center" }}>Types</h2>
          <TypesList
            allBodyTypes={allBodyTypes}
            allFuelTypes={allFuelTypes}
            allGearShiftTypes={allGearShiftTypes}
            handleClickType={handleClickType}
          />
        </Grid>
      </Grid>
      {addDialogOpened !== dialogEnum.UNOPEND && addingDialog()}
    </>
  );
}
