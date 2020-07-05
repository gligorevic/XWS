import React, { useState, useEffect } from "react";
import AppBar from "@material-ui/core/AppBar";
import Dialog from "@material-ui/core/Dialog";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
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
import { getPricelistItems } from "../../../store/actions/pricelist";
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
    margin: theme.spacing(1),
    minWidth: 120,
  },
}));

const AddPricelistItems = ({ getPricelistItems, items, match }) => {
  useEffect(() => {
    getPricelistItems(`${match.params.pricelistId}`);
  }, []);
  const [success, setSuccess] = useState();

  const handleSubmit = async (e) => {};
  const handleChange = (event) => {};
  const handleChangeTextField = (e) => {
    setState({ ...state, [e.target.name]: e.target.value });
  };
  const [state, setState] = React.useState({
    pricePerDay: 0,
    pricePerKm: 0,
    priceCollisionDamage: 0,
    minNumberDays: 0,
    percentage: 0,
  });
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
                onChange={handleChange}
              >
                <MenuItem value={10}>Ten</MenuItem>
                <MenuItem value={20}>Twenty</MenuItem>
                <MenuItem value={30}>Thirty</MenuItem>
              </Select>
            </FormControl>
            <TextField
              type="number"
              name="pricePerDay"
              onChange={handleChangeTextField}
              value={state.pricePerDay}
              required
              label="Price per day"
            />
            <TextField
              type="number"
              name="pricePerKm"
              onChange={handleChangeTextField}
              value={state.pricePerKm}
              required
              label="Price per km"
            />
            <TextField
              type="number"
              name="priceCollisionDamage"
              onChange={handleChangeTextField}
              value={state.priceCollisionDamage}
              required
              label="Price for collision damage"
            />
            <TextField
              type="number"
              name="minNumberDays"
              onChange={handleChangeTextField}
              value={state.minNumberDays}
              required
              label="Min number of days for discount"
            />
            <TextField
              type="number"
              name="percentage"
              onChange={handleChangeTextField}
              value={state.percentage}
              required
              label="Discount percentage"
            />
          </Grid>
        </ExpansionPanelDetails>
      </ExpansionPanel>
    </>
  );
};

const mapStateToProps = (state) => ({
  items: state.pricelist.pricelistItems,
});

export default connect(mapStateToProps, { getPricelistItems })(
  AddPricelistItems
);
