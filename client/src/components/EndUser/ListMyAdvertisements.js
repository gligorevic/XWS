import React, { useEffect } from "react";
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
import { getUserAdvertisements } from "../../store/actions/advertisement";
import { Button } from "@material-ui/core";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import Paper from "@material-ui/core/Paper";
import Checkbox from "@material-ui/core/Checkbox";
import AddResPeriodDialog from "../EndUser/AddResPeriodDialog";

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

const ListMyAdvertisements = ({ getUserAdvertisements, ads, history }) => {
  useEffect(() => {
    getUserAdvertisements();
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

  const sort = (ads) => {
    return ads.sort((a, b) => {
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
        {ads && (
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
                        active={orderBy === "kmRestriction"}
                        direction={order}
                        onClick={() => handleRequestSort("kmRestriction")}
                      >
                        Km restriction
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "priceFrom"}
                        direction={order}
                        onClick={() => handleRequestSort("priceFrom")}
                      >
                        price
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "numberChildSeats"}
                        direction={order}
                        onClick={() => handleRequestSort("numberChildSeats")}
                      >
                        Number of children seats
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "collisionDamage"}
                        direction={order}
                        onClick={() => handleRequestSort("collisionDamage")}
                      >
                        Collision Damage Waiver
                      </TableSortLabel>
                    </TableCell>
                    <TableCell></TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {ads &&
                    ads.length > 0 &&
                    sort(ads)
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
                                {row.kmRestriction}
                              </TableCell>

                              <TableCell align="left">
                                {row.priceFrom}
                              </TableCell>
                              <TableCell align="left">
                                {row.numberChildSeats}
                              </TableCell>
                              <TableCell align="left">
                                <Checkbox
                                  checked={row.collisionDamage}
                                  name="checkedB"
                                  color="primary"
                                />
                              </TableCell>
                              <TableCell align="right">
                                <AddResPeriodDialog id={row.id} />
                              </TableCell>
                              <TableCell align="right">
                                <Button
                                  variant="outlined"
                                  color="primary"
                                  onClick={() => {}}
                                >
                                  View reservation periods
                                </Button>
                              </TableCell>
                            </TableRow>
                          </>
                        );
                      })}
                  {rowsPerPage -
                    Math.min(rowsPerPage, ads.length - page * rowsPerPage) >
                    0 && (
                    <TableRow
                      style={{
                        height:
                          53 *
                          (rowsPerPage -
                            Math.min(
                              rowsPerPage,
                              ads.length - page * rowsPerPage
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
              count={ads.length}
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
  ads: state.advertisement.myAdvertisements,
});

export default withRouter(
  connect(mapStateToProps, { getUserAdvertisements })(ListMyAdvertisements)
);
