import React, { useState, useEffect } from "react";
import AppBar from "@material-ui/core/AppBar";
import Dialog from "@material-ui/core/Dialog";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import AddCarSelect from "./AddCarSelect";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";
import { connect } from "react-redux";
import { addCar } from "../../../store/actions/cars";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

function AddCarFullScreen({ open, setOpen, userEmail, addCar }) {
  const [brand, setBrand] = useState();
  const [model, setModel] = useState();
  const [gearShift, setGearShif] = useState();
  const [fuelType, setFuelType] = useState();
  const [body, setBody] = useState();
  const [kmPassed, setKmPassed] = useState();

  const [images, setImages] = useState(null);

  const [success, setSuccess] = useState();

  useEffect(() => {
    setModel();
  }, [brand]);

  useEffect(() => {
    setGearShif();
    setFuelType();
    setBody();
  }, [model]);

  const handleChangeImage = (e) => {
    setImages(
      Array.from(e.target.files).map((file) => ({
        url: URL.createObjectURL(file),
        file,
        name: file.name,
      }))
    );
  };

  const handleSubmit = async (e) => {
    let formData = new FormData();

    formData.append(
      "car",
      new Blob(
        [
          JSON.stringify({
            brandName: brand.brandName,
            modelName: model.modelName,
            gearShiftName: gearShift.gearShiftName,
            fuelTypeName: fuelType.fuelTypeName,
            bodyName: body.bodyTypeName,
            kmPassed,
            userEmail,
          }),
        ],
        {
          type: "application/json",
        }
      )
    );
    images.forEach((img) => formData.append("file", img.file));
    const res = await addCar(formData, images[0].name);
    console.log(res);
    if (res.status >= 200 && res.status < 300) {
      await setOpen(false);
      setSuccess(true);
    }
  };

  return (
    <>
      <Dialog fullScreen open={open} onClose={() => setOpen(false)}>
        <AppBar>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={() => setOpen(false)}
              aria-label="close"
            >
              <CloseIcon />
            </IconButton>
            <Typography variant="h6">Add advertisement</Typography>
          </Toolbar>
        </AppBar>
        <Grid
          container
          spacing={3}
          style={{
            margin: "120px auto",
            border: "1px solid #f1f1f1",
            borderRadius: 7,
            maxWidth: 500,
            boxShadow: "0px 0px 17px 0px #4caf5070",
          }}
          justify="space-around"
          alignItems="center"
        >
          <Grid item sm={12}>
            <h1
              style={{ color: "#a1a1a1", textAlign: "center", fontWeight: 500 }}
            >
              Add new car
            </h1>
          </Grid>
          <Grid item sm={12} md={6}>
            <AddCarSelect
              value={brand}
              setValue={setBrand}
              urlEndpoint={"/car-info/brand"}
              description="Brand"
              field="brandName"
              fetch
            />
          </Grid>
          <Grid item sm={12} md={6}>
            <AddCarSelect
              value={model}
              setValue={setModel}
              urlEndpoint={`/car-info/brand/${brand && brand.brandName}/model`}
              description="Model"
              field="modelName"
              fetch={!!brand}
            />
          </Grid>
          <Grid item sm={12} md={6}>
            <AddCarSelect
              value={gearShift}
              setValue={setGearShif}
              urlEndpoint={`/car-info/model/${
                model && model.id
              }/gear-shift-type`}
              description="Gear Shift"
              field="gearShiftName"
              fetch={!!model}
            />
          </Grid>
          <Grid item sm={12} md={6}>
            <AddCarSelect
              value={fuelType}
              setValue={setFuelType}
              urlEndpoint={`/car-info/model/${model && model.id}/fuel-type`}
              description="Fuel Type"
              field="fuelTypeName"
              fetch={!!model}
            />
          </Grid>
          <Grid item sm={12} md={6}>
            <AddCarSelect
              value={body}
              setValue={setBody}
              urlEndpoint={`/car-info/model/${model && model.id}/body-type`}
              description="Body"
              field="bodyTypeName"
              fetch={!!model}
            />
          </Grid>
          <Grid item sm={12} md={6}>
            <TextField
              label="Km passed"
              type="number"
              style={{ margin: "10px 8%" }}
              value={kmPassed}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={(e) => setKmPassed(e.target.value)}
            />
          </Grid>
          <Grid item sm={12}>
            <input
              accept="image/*"
              id="icon-button-file"
              type="file"
              multiple
              onChange={handleChangeImage}
            />
          </Grid>
          <Grid item sm={12} style={{ textAlign: "center" }}>
            <Button
              variant="contained"
              onClick={handleSubmit}
              disabled={
                !gearShift || !body || !fuelType || kmPassed === undefined
              }
            >
              Confirm
            </Button>
          </Grid>
        </Grid>
      </Dialog>
      <Snackbar
        open={!open && success}
        autoHideDuration={4500}
        onClose={() => setSuccess(false)}
      >
        <Alert onClose={() => setSuccess(false)} severity="success">
          Successfully added new car!
        </Alert>
      </Snackbar>
    </>
  );
}

const mapStateToProps = (state) => ({
  userEmail: state.user.user.username,
});

export default connect(mapStateToProps, { addCar })(AddCarFullScreen);
