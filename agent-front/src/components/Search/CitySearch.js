// *https://www.registers.service.gov.uk/registers/country/use-the-api*
import React, { useState } from "react";
import Axios from "axios";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";

export default function CitySearch({ cityName, setCityName, error, setError }) {
  const [open, setOpen] = useState(false);
  const [options, setOptions] = useState([]);

  const [loading, setLoading] = useState(false);

  const handleChange = async (e) => {
    setCityName(e.target.value);
    setLoading(true);
    if (e.target.value.length > 0) {
      const response = await Axios.get(`/city/${e.target.value}`);
      setOptions(response.data);
    } else {
      setOptions([]);
    }
    setLoading(false);
  };

  const handleChoose = async (e, value) => {
    if (value !== null) {
      setCityName(value.name);
      const response = await Axios.get(`/city/${value.name}`);
      setOptions(response.data);
    }
  };

  return (
    <Autocomplete
      id="asynchronous-demo"
      style={{ width: 300 }}
      open={open}
      onOpen={() => {
        setOpen(true);
        setError(false);
      }}
      onClose={() => {
        setOpen(false);
        setError(false);
      }}
      onChange={handleChoose}
      getOptionSelected={(option, value) => option.name === value.name}
      getOptionLabel={(option) => option.name}
      options={options}
      loading={loading}
      noOptionsText={
        cityName.length > 0 ? "No cities found" : "Type in city name"
      }
      renderInput={(params) => (
        <TextField
          error={error}
          {...params}
          error={error}
          helperText={error && "City doesn't exist, chose one."}
          label="City name"
          variant="outlined"
          onChange={handleChange}
          value={cityName}
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <React.Fragment>
                {loading ? (
                  <CircularProgress color="inherit" size={20} />
                ) : null}
                {params.InputProps.endAdornment}
              </React.Fragment>
            ),
          }}
        />
      )}
    />
  );
}
