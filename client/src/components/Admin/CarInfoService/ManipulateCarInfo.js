import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Alert from "@material-ui/lab/Alert";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Snackbar from "@material-ui/core/Snackbar";

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
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
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

  const [loading, setLoading] = React.useState(false);
  const [openSuccess, setOpenSuccess] = React.useState(false);
  const [openFailure, setOpenFailure] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

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
      setAddDialogOpened(dialogEnum.UNOPEND);
      setLoading(true);
      const newModel = {
        modelName: newEntityData,
        brandName: selectedBrand.brandName,
      };
      const response = await Axios.post("/car-info/model", newModel).catch(
        (error) => {
          if (error.response && error.response.status === 400) {
            setLoading(false);
            setOpenFailure(true);
            setErrorMessage(error.response.data);
          }
        }
      );
      if (response.status >= 200 && response.status < 300) {
        setLoading(false);
        setOpenSuccess(true);
        setAllModels((oldState) => [...oldState, response.data]);
      }
    } catch (e) {
      console.log(e);
    }
  };

  const handleSubmitBrand = async (e) => {
    try {
      e.preventDefault();
      setAddDialogOpened(dialogEnum.UNOPEND);
      setLoading(true);
      const response = await Axios.post(`/car-info/brand`, newEntityData, {
        headers: { "Content-Type": "text/plain" },
      }).catch((error) => {
        if (error.response && error.response.status === 400) {
          setLoading(false);
          setOpenFailure(true);
          setErrorMessage(error.response.data);
        }
      });

      if (response.status >= 200 && response.status < 300) {
        setLoading(false);
        setOpenSuccess(true);
        setAllBrands((oldBrands) => [...oldBrands, response.data]);
      }
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

  const handleCloseSuccess = () => {
    setOpenSuccess(false);
  };

  const handleCloseError = () => {
    setOpenFailure(false);
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
            setLoading={setLoading}
            setOpenFailure={setOpenFailure}
            setOpenSuccess={setOpenSuccess}
            setErrorMessage={setErrorMessage}
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
                setAllBrands={setAllBrands}
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
                setAllModels={setAllModels}
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
            setAllFuelTypes={setAllFuelTypes}
            setAllBodyTypes={setAllBodyTypes}
            setAllGearShiftTypes={setAllGearShiftTypes}
            handleClickType={handleClickType}
          />
        </Grid>
      </Grid>
      {addDialogOpened !== dialogEnum.UNOPEND && addingDialog()}

      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Snackbar
        open={openSuccess}
        autoHideDuration={3000}
        onClose={handleCloseSuccess}
      >
        <Alert onClose={handleCloseSuccess} severity="success">
          Successfully added.
        </Alert>
      </Snackbar>

      <Snackbar
        open={openFailure}
        autoHideDuration={3000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </>
  );
}
