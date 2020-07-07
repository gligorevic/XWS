import React, { useState, useEffect } from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";
import { connect } from "react-redux";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import { makeStyles } from "@material-ui/core/styles";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import {
  getPricelistItems,
  getAdsForPricelist,
} from "../../../store/actions/pricelist";
import axios from "axios";
import AttachMoneyIcon from "@material-ui/icons/AttachMoney";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: theme.typography.fontWeightRegular,
  },
  formControl: {
    minWidth: 120,
    marginTop: 20,
  },
}));

const AddPricelistItems = ({
  getPricelistItems,
  items,
  match,
  getAdsForPricelist,
  ads,
}) => {
  useEffect(() => {
    getPricelistItems(`${match.params.pricelistId}`);
  }, []);
  const [success, setSuccess] = useState();
  const [openFailure, setOpenFailure] = React.useState(false);
  const [state, setState] = React.useState({
    pricePerDay: 0,
    pricePerKm: 0,
    priceCollisionDamage: 0,
    minNumberDays: 0,
    percentage: 0,
    advertisementId: "",
    brandName: "",
    modelName: "",
    priceListId: match.params.pricelistId,
  });
  const [collisionDamageDisabled, setCollisionDamageDisabled] = React.useState(
    true
  );

  const [kmRestrictionDisabled, setKmRestrictionDisabled] = React.useState(
    true
  );

  const [errorMessage, setErrorMessage] = React.useState(
    "Something went wrong"
  );

  const handleCloseError = () => {
    setOpenFailure(false);
  };

  const handleSubmit = async (e) => {
    const resp = await axios.post(`/price-list/items`, state).catch((error) => {
      if (error.response.status === 406) {
        setErrorMessage(
          "Price list item for this advertisement already exists."
        );
        setOpenFailure(true);
      } else {
        setErrorMessage("Something went wrong.");
        setOpenFailure(true);
      }
    });
    setState({
      pricePerDay: 0,
      pricePerKm: 0,
      priceCollisionDamage: 0,
      minNumberDays: 0,
      percentage: 0,
      advertisementId: "",
      brandName: "",
      modelName: "",
      priceListId: match.params.pricelistId,
    });
    if (resp && resp.status >= 200 && resp.status < 300) {
      getPricelistItems(`${match.params.pricelistId}`);
    }
  };
  const handleChange = (event, ad) => {
    setState({
      ...state,
      advertisementId: ad.id,
      brandName: ad.brandName,
      modelName: ad.modelName,
      pricePerDay: 0,
      pricePerKm: 0,
      priceCollisionDamage: 0,
      minNumberDays: 0,
      percentage: 0,
    });
    setCollisionDamageDisabled(ad.collisionDamage ? false : true);
    setKmRestrictionDisabled(
      ad.kmRestriction && ad.kmRestriction > 0 ? false : true
    );
  };
  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };

  const classes = useStyles();
  return (
    <>
      <ExpansionPanel style={{ margin: 30 }}>
        <ExpansionPanelSummary
          expandIcon={<ExpandMoreIcon />}
          aria-label="Expand"
          aria-controls="additional-actions3-content"
          id="additional-actions3-header"
          style={{ fontWeight: 500, fontSize: 18 }}
        >
          Add new item
        </ExpansionPanelSummary>
        <ExpansionPanelDetails>
          <Grid container spacing={3}>
            <FormControl className={classes.formControl}>
              <InputLabel id="demo-simple-select-label">
                Select advertisement
              </InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                onOpen={() => {
                  getAdsForPricelist();
                }}
              >
                {ads.map((ad) => (
                  <MenuItem
                    value={ad}
                    key={ad.id}
                    onClick={(e) => {
                      handleChange(e, ad);
                    }}
                  >
                    {ad.brandName + ` - ` + ad.modelName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              type="number"
              name="pricePerDay"
              onChange={handleChangeTextField}
              value={state.pricePerDay}
              required
              style={{ margin: 20 }}
              label="Price per day"
            />
            <TextField
              style={{ margin: 20 }}
              type="number"
              name="pricePerKm"
              disabled={kmRestrictionDisabled}
              onChange={handleChangeTextField}
              value={state.pricePerKm}
              required
              label="Price per km"
            />
            <TextField
              style={{ margin: 20 }}
              type="number"
              name="priceCollisionDamage"
              onChange={handleChangeTextField}
              disabled={collisionDamageDisabled}
              value={state.priceCollisionDamage}
              required
              label="Price for collision damage"
            />
            <TextField
              style={{ margin: 20 }}
              type="number"
              name="minNumberDays"
              onChange={handleChangeTextField}
              value={state.minNumberDays}
              required
              label="Min number of days for discount"
            />
            <TextField
              style={{ margin: 20 }}
              type="number"
              name="percentage"
              onChange={handleChangeTextField}
              value={state.percentage}
              required
              label="Discount percentage"
            />
          </Grid>
          <Button onClick={handleSubmit} variant="contained" color="primary">
            Add
          </Button>
        </ExpansionPanelDetails>
      </ExpansionPanel>

      <Grid container spacing={3}>
        {items &&
          items.length > 0 &&
          items.map((item) => (
            <Grid item xs={6}>
              <ExpansionPanel style={{ margin: 30 }} expanded={true}>
                <ExpansionPanelSummary
                  aria-label="Expand"
                  aria-controls="additional-actions3-content"
                  id={item.advertisementId}
                  style={{ fontWeight: 500, fontSize: 18 }}
                >
                  {item.brandName + ` - ` + item.modelName}
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                  <Grid container spacing={3}>
                    <Grid item xs={6}>
                      <List className={classes.root}>
                        <ListItem>
                          <ListItemIcon>
                            <AttachMoneyIcon />
                          </ListItemIcon>
                          <ListItemText
                            id="switch-list-label-wifi"
                            primary={`Price per day:  ` + item.pricePerDay}
                          />
                        </ListItem>
                        <ListItem>
                          <ListItemIcon>
                            <AttachMoneyIcon />
                          </ListItemIcon>
                          <ListItemText
                            id="switch-list-label-bluetooth"
                            primary={`Price per km:  ` + item.pricePerKm}
                          />
                        </ListItem>
                        <ListItem>
                          <ListItemIcon>
                            <AttachMoneyIcon />
                          </ListItemIcon>
                          <ListItemText
                            id="switch-list-label-wifi"
                            primary={
                              `Price for collision damage:  ` +
                              item.priceCollisionDamage
                            }
                          />
                        </ListItem>
                      </List>
                    </Grid>
                    <Grid item xs={6}>
                      <List className={classes.root}>
                        <ListItem>
                          <ListItemText
                            id="switch-list-label-wifi"
                            primary={
                              `Minimum number of days for discount:  ` +
                              item.minNumberDays
                            }
                          />
                        </ListItem>
                        <ListItem>
                          <ListItemText
                            id="switch-list-label-bluetooth"
                            primary={
                              `Discount percentage:  ` + item.percentage + `%`
                            }
                          />
                        </ListItem>
                      </List>
                    </Grid>
                  </Grid>
                </ExpansionPanelDetails>
              </ExpansionPanel>
            </Grid>
          ))}
      </Grid>
      <Snackbar
        open={openFailure}
        autoHideDuration={2000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {errorMessage}
        </Alert>
      </Snackbar>
    </>
  );
};

const mapStateToProps = (state) => ({
  items: state.pricelist.pricelistItems,
  ads: state.pricelist.adsForPricelist,
});

export default connect(mapStateToProps, {
  getPricelistItems,
  getAdsForPricelist,
})(AddPricelistItems);
