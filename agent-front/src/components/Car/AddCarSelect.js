import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import FormHelperText from "@material-ui/core/FormHelperText";
import Select from "@material-ui/core/Select";
import Axios from "axios";

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    width: "84%",
    margin: "10px 8%",
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
}));

export default function SimpleSelect({
  value,
  setValue,
  urlEndpoint,
  description,
  field,
  fetch,
}) {
  const classes = useStyles();
  const [options, setOptions] = useState([]);

  useEffect(() => {
    console.log(description);
    (async () => {
      if (fetch) {
        const res = await Axios.get(urlEndpoint);
        console.log(res.data);
        setOptions(res.data);
      } else {
        setValue(null);
        setOptions([]);
      }
    })();
  }, [fetch]);

  const handleChange = async (event) => {
    await setValue(null);
    await setValue(event.target.value);
  };

  return (
    <div>
      <FormControl className={classes.formControl} disabled={!fetch}>
        <InputLabel>{description}</InputLabel>
        <Select value={value ? value : ""} onChange={handleChange}>
          {options.map((option) => (
            <MenuItem value={option} key={option[field]}>
              {option[field]}
            </MenuItem>
          ))}
        </Select>
        {!fetch && (
          <FormHelperText>
            {description === "Model"
              ? "First select brand"
              : "First select model"}
          </FormHelperText>
        )}
      </FormControl>
    </div>
  );
}
