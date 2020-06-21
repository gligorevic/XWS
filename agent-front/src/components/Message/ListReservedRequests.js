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
import { getReservedRequests } from "../../store/actions/request";
import { Button } from "@material-ui/core";
import clsx from "clsx";
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
    minWidth: 600,
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
  paper: {
    padding: theme.spacing(1),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
  fixedHeight: {
    height: 420,
  },
}));

const ListReservedRequests = ({
  getReservedRequests,
  requests,
  history,
  setSelecetedRequestId,
}) => {
  useEffect(() => {
    getReservedRequests();
  }, []);
  const classes = useStyles();
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

  const sort = (requests) => {
    return requests.sort((a, b) => {
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
      } else if (orderBy === "cityName") {
        return order === "asc"
          ? a.cityName < b.cityName
            ? 1
            : -1
          : b.cityName < a.cityName
          ? 1
          : -1;
      } else {
        return order === "asc"
          ? a.price < b.price
            ? 1
            : -1
          : b.price < a.price
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

  const handleClick = (e, id) => {
    setSelecetedRequestId(id);
  };

  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

  return (
    <>
      <div className={classes.root}>
        {requests && (
          <>
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
                        Start date
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "modelName"}
                        direction={order}
                        onClick={() => handleRequestSort("modelName")}
                      >
                        End date
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "cityName"}
                        direction={order}
                        onClick={() => handleRequestSort("cityName")}
                      >
                        Status
                      </TableSortLabel>
                    </TableCell>
                    <TableCell align="left">
                      <TableSortLabel
                        active={orderBy === "price"}
                        direction={order}
                        onClick={() => handleRequestSort("price")}
                      >
                        Brand - model
                      </TableSortLabel>
                    </TableCell>

                    <TableCell></TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {requests &&
                    requests.length > 0 &&
                    sort(requests)
                      .slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
                      .map((row, index) => {
                        return (
                          <>
                            <TableRow
                              Button
                              role="checkbox"
                              tabIndex={-1}
                              key={row.id}
                            >
                              <TableCell component="th" allign="left">
                                {row.startDate}
                              </TableCell>
                              <TableCell align="left">{row.endDate}</TableCell>
                              <TableCell align="left">
                                {row.paidState}
                              </TableCell>

                              <TableCell align="left">
                                {row.advertisement.car.brand.brandName +
                                  " - " +
                                  row.advertisement.car.model.modelName}
                              </TableCell>

                              <TableCell align="right">
                                <Button
                                  variant="outlined"
                                  color="primary"
                                  onClick={(e) => {
                                    handleClick(e, row.id);
                                  }}
                                >
                                  View messages
                                </Button>
                              </TableCell>
                              <TableCell align="right"></TableCell>
                            </TableRow>
                          </>
                        );
                      })}
                  {rowsPerPage -
                    Math.min(
                      rowsPerPage,
                      requests.length - page * rowsPerPage
                    ) >
                    0 && (
                    <TableRow
                      style={{
                        height:
                          53 *
                          (rowsPerPage -
                            Math.min(
                              rowsPerPage,
                              requests.length - page * rowsPerPage
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
              count={requests.length}
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
          </>
        )}
      </div>
    </>
  );
};

const mapStateToProps = (state) => ({
  requests: state.request.reservedRequests,
});

export default withRouter(
  connect(mapStateToProps, { getReservedRequests })(ListReservedRequests)
);
