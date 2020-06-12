import React, { useEffect, useState } from "react";
import { withRouter } from "react-router";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import { Paper, TableCell } from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import TableSortLabel from "@material-ui/core/TableSortLabel";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    marginTop: theme.spacing(3),
  },
  grow: {
    flexGrow: 1,
  },
  container: {
    padding: "10px 250px",
  },
  paper: {
    align: "center",
    margin: "5% 10%",
  },
  table: {
    minWidth: 750,
    backgroundColor: "#fbfbfb",
  },
  tableWrapper: {
    overflowX: "auto",
  },
}));

function ManipulateRequests({ match, history }) {
  const classes = useStyles();

  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("name");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const [requests, setRequests] = useState();

  useEffect(() => {
    (async () => {
      try {
        const res = await Axios.get(`/request/ad/${match.params.adId}`);
        console.log(res);
        setRequests(res.data);
      } catch (e) {
        console.log(e);
      }
    })();
  }, []);

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const sort = (requests) => {
    return requests.sort((a, b) => {
      if (orderBy === "userSentRequest") {
        return order === "asc"
          ? a.userSentRequest < b.userSentRequest
            ? 1
            : -1
          : b.userSentRequest < a.userSentRequest
          ? 1
          : -1;
      } else if (orderBy === "paidState") {
        return order === "asc"
          ? a.paidState < b.paidState
            ? 1
            : -1
          : b.paidState < a.paidState
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
    <div>
      <AppBar position="static" color="default">
        <Toolbar>
          <span className={classes.grow}>
            <Button
              variant="contained"
              color="primary"
              onClick={() => history.push(`/user`)}
            >
              Go Back
            </Button>
          </span>
        </Toolbar>
      </AppBar>
      {requests && (
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
                      active={orderBy === "userSentRequest"}
                      direction={order}
                      onClick={() => handleRequestSort("userSentRequest")}
                    >
                      User Sent Request
                    </TableSortLabel>
                  </TableCell>
                  <TableCell align="left">From</TableCell>
                  <TableCell align="left">To</TableCell>
                  <TableCell align="left">
                    <TableSortLabel
                      active={orderBy === "paidState"}
                      direction={order}
                      onClick={() => handleRequestSort("paidState")}
                    >
                      STATUS
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
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row, index) => {
                      return (
                        <>
                          <TableRow
                            hover
                            role="checkbox"
                            tabIndex={-1}
                            key={row.userSentEmail}
                          >
                            <TableCell component="th" allign="left">
                              {row.userSentRequest}
                            </TableCell>
                            <TableCell align="left">{row.startDate}</TableCell>
                            <TableCell align="left">{row.endDate}</TableCell>

                            <TableCell align="left">{row.paidState}</TableCell>
                            <TableCell align="left">
                              <Button
                                size="medium"
                                color="primary"
                                variant="contained"
                              >
                                Accept
                              </Button>
                            </TableCell>
                            <TableCell align="left">
                              <Button
                                size="medium"
                                variant="contained"
                                style={{ backgroundColor: "#d66" }}
                              >
                                Decline
                              </Button>
                            </TableCell>
                          </TableRow>
                        </>
                      );
                    })}
                {rowsPerPage -
                  Math.min(rowsPerPage, requests.length - page * rowsPerPage) >
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
        </Paper>
      )}
      <div className={classes.container}></div>
    </div>
  );
}

export default withRouter(ManipulateRequests);
