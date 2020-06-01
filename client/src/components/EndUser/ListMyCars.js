import React, { useEffect } from "react";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { getCars, setCarForAdvertisement } from "../../store/actions/cars";
import { Button } from "@material-ui/core";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Paper from "@material-ui/core/Paper";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    marginTop: theme.spacing(3),
  },
  paper: {
    width: "100%",
    marginBottom: theme.spacing(2),
  },
  table: {
    minWidth: 750,
  },
  tableWrapper: {
    overflowX: "auto",
  },
  visuallyHidden: {
    border: 0,
    clip: "rect(0 0 0 0)",
    height: 1,
    margin: -1,
    overflow: "hidden",
    padding: 0,
    position: "absolute",
    top: 20,
    width: 1,
  },
  formControl: {
    minWidth: 120,
  },
  margin: {
    margin: theme.spacing(1),
  },
  extendedIcon: {
    marginRight: theme.spacing(1),
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: "#fff",
  },
}));

const ListMyCars = ({ getCars, cars, history, setCarForAdvertisement }) => {
  useEffect(() => {
    getCars();
  }, []);
  const classes = useStyles();
  const [loading, setLoading] = React.useState(false);
  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("name");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const sort = (cars) => {
    return cars.sort((a, b) => {
      if (orderBy === "brandName") {
        return order === "asc"
          ? a.brandName < b.brandName
            ? 1
            : -1
          : b.brandName < a.brandName
          ? 1
          : -1;
      } else if (orderBy === "modelName") {
        return order === "asc"
          ? a.modelName < b.modelName
            ? 1
            : -1
          : b.modelName < a.modelName
          ? 1
          : -1;
      } else if (orderBy === "gearShiftName") {
        return order === "asc"
          ? a.gearShiftName < b.gearShiftName
            ? 1
            : -1
          : b.gearShiftName < a.gearShiftName
          ? 1
          : -1;
      } else if (orderBy === "fuelTypeName") {
        return order === "asc"
          ? a.fuelTypeName < b.fuelTypeName
            ? 1
            : -1
          : b.fuelTypeName < a.fuelTypeName
          ? 1
          : -1;
      } else if (orderBy === "bodyName") {
        return order === "asc"
          ? a.bodyName < b.bodyName
            ? 1
            : -1
          : b.bodyName < a.bodyName
          ? 1
          : -1;
      } else {
        return order === "asc"
          ? a.kmPassed < b.kmPassed
            ? 1
            : -1
          : b.kmPassed < a.kmPassed
          ? 1
          : -1;
      }
    });
  };

  const handleRequestSort = (property, event) => {
    const isDesc = orderBy === property && order === "desc";
    setOrder(isDesc ? "asc" : "desc");
    setOrderBy(property);
  };

  return (
    <>
      <div className={classes.root}>
        {cars && (
          <Paper className={classes.paper}>
            <div className={classes.tableWrapper}>
              <Table
                className={classes.table}
                aria-labelledby="tableTitle"
                aria-label="enhanced table"
              >
                <TableHead>
                  <TableRow>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "brandName"}
                        direction={order}
                        onClick={() => handleRequestSort("brandName")}
                      >
                        Brand
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "modelName"}
                        direction={order}
                        onClick={() => handleRequestSort("modelName")}
                      >
                        Model
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "gearShiftName"}
                        direction={order}
                        onClick={() => handleRequestSort("gearShiftName")}
                      >
                        Gear shift
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "fuelTypeName"}
                        direction={order}
                        onClick={() => handleRequestSort("fuelTypeName")}
                      >
                        Fuel Type
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "bodyName"}
                        direction={order}
                        onClick={() => handleRequestSort("bodyName")}
                      >
                        Body
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "kmPasssed"}
                        direction={order}
                        onClick={() => handleRequestSort("kmPasssed")}
                      >
                        KM passed
                      </TableSortLabel>
                    </TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {cars &&
                    cars.length > 0 &&
                    sort(cars)
                      .slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
                      .map((row, index) => {
                        return (
                          <>
                            <TableRow
                              hover
                              role="checkbox"
                              tabIndex={-1}
                              key={row.brandName}
                            >
                              <TableCell component="th" allign="left">
                                {row.brandName}
                              </TableCell>
                              <TableCell align="left">
                                {row.modelName}
                              </TableCell>
                              <TableCell align="left">
                                {row.gearShiftName}
                              </TableCell>

                              <TableCell align="left">
                                {row.fuelTypeName}
                              </TableCell>
                              <TableCell align="left">{row.bodyName}</TableCell>
                              <TableCell align="left">{row.kmPassed}</TableCell>
                              <TableCell align="right">
                                <Button
                                  variant="outlined"
                                  color="secondary"
                                  onClick={() => {
                                    setCarForAdvertisement(row);
                                    history.push({
                                      pathname: `/advertisement`,
                                    });
                                  }}
                                >
                                  Add advertisement
                                </Button>
                              </TableCell>
                            </TableRow>
                          </>
                        );
                      })}
                  {rowsPerPage -
                    Math.min(rowsPerPage, cars.length - page * rowsPerPage) >
                    0 && (
                    <TableRow
                      style={{
                        height:
                          53 *
                          (rowsPerPage -
                            Math.min(
                              rowsPerPage,
                              cars.length - page * rowsPerPage
                            )),
                      }}
                    >
                      <TableCell colSpan={8} />
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </div>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={cars.length}
              rowsPerPage={rowsPerPage}
              page={page}
              backIconButtonProps={{
                "aria-label": "previous page",
              }}
              nextIconButtonProps={{
                "aria-label": "next page",
              }}
              onChangePage={handleChangePage}
              onChangeRowsPerPage={handleChangeRowsPerPage}
            />
          </Paper>
        )}
        <Backdrop className={classes.backdrop} open={loading}>
          <CircularProgress color="inherit" />
        </Backdrop>
      </div>
    </>
  );
};

const mapStateToProps = (state) => ({
  cars: state.cars.myCars,
});

export default withRouter(
  connect(mapStateToProps, { getCars, setCarForAdvertisement })(ListMyCars)
);
