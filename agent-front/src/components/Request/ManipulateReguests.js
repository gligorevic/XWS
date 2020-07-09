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
import Checkbox from "@material-ui/core/Checkbox";

import AcceptDialog from "./AcceptDialog";
import DeclineDialog from "./DeclineDialog";
// import CommentDialog from "../../Dialogs/Comment/CommentDialog";

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

function ManipulateRequests({ match, history, user }) {
  const classes = useStyles();

  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("name");
  const [page, setPage] = React.useState(0);
  const [request, setRequest] = React.useState(null);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const [requests, setRequests] = useState([]);

  const [openAcceptDialog, setOpenAcceptDialog] = React.useState(false);
  const [openDeclineDialog, setOpenDeclineDialog] = React.useState(false);
  const [openedCommentDialog, setOpenedCommentDialog] = useState(-1);

  const [requestsInBundle, setRequestsInBundle] = React.useState(null);

  useEffect(() => {
    (async () => {
      try {
        const res = await Axios.get(`/request/ad/${match.params.adId}`);
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

  const handleOpenCommentDialog = (event, row) => {
    setRequest(row);
    setOpenedCommentDialog(row.id);
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

  const handleAccept = async (e, request) => {
    setRequest(request);
    if (request.inBundle === true) {
      const res = await Axios.get(`/request/bundle/${request.id}`);
      setRequestsInBundle(res.data);
      setOpenAcceptDialog(true);
    } else {
      request.paidState = "RESERVED";
      setRequest(request);
      await Axios.put(`/request/${request.id}`, request);
    }
  };

  const handleDecline = async (e, request) => {
    setRequest(request);
    if (request.inBundle === true) {
      const res = await Axios.get(`/request/bundle/${request.id}`);
      setRequestsInBundle(res.data);
      setOpenDeclineDialog(true);
    } else {
      request.paidState = "CANCELED";
      setRequest(request);
      await Axios.put(`/request/${request.id}`, request);
    }
  };

  const checkIfOverlaping = (row) =>
    requests.some(
      (r) =>
        r.paidState === "RESERVED" ||
        (r.paidState === "PAID" &&
          ((r.startDate <= row.startDate && r.endDate >= row.startDate) ||
            (r.startDate <= row.endDate && r.endDate >= row.endDate) ||
            (r.startDate >= row.startDate && r.endDate <= row.endDate)))
    );

  return (
    <div>
      <AppBar position="static" color="default">
        <Toolbar>
          <span className={classes.grow}>
            <Button
              variant="contained"
              color="primary"
              onClick={() =>
                history.push({ pathname: `/agent`, state: { tab: 6 } })
              }
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
                  <TableCell align="left">In Bundle</TableCell>
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
                            <TableCell align="center">
                              <Checkbox checked={row.inBundle} disabled />
                            </TableCell>
                            <TableCell align="left">
                              {row.paidState === "RESERVED" && (
                                <>
                                  <Button
                                    size="medium"
                                    variant="contained"
                                    style={{
                                      backgroundColor: "#3a3",
                                      opacity: "0.5",
                                    }}
                                  >
                                    Reserved
                                  </Button>
                                </>
                              )}
                              {(row.paidState === "CANCELED" ||
                                (row.paidState === "PENDING" &&
                                  checkIfOverlaping(row))) && (
                                <Button
                                  color="primary"
                                  variant="contained"
                                  disabled
                                  size="medium"
                                >
                                  Accept
                                </Button>
                              )}
                              {row.paidState === "PENDING" &&
                                !checkIfOverlaping(row) && (
                                  <Button
                                    onClick={(e) => handleAccept(e, row)}
                                    size="medium"
                                    color="primary"
                                    variant="contained"
                                  >
                                    Accept
                                  </Button>
                                )}
                              {row.paidState === "PAID" && (
                                <Button
                                  size="medium"
                                  style={{
                                    backgroundColor: "#03a9f4",
                                    opacity: 0.6,
                                  }}
                                  variant="contained"
                                >
                                  Paid
                                </Button>
                              )}
                            </TableCell>
                            <TableCell align="left">
                              {row.paidState !== "PENDING" &&
                                row.paidState !== "PAID" && (
                                  <Button
                                    disabled
                                    size="medium"
                                    variant="contained"
                                    style={{ backgroundColor: "#d66" }}
                                  >
                                    Decline
                                  </Button>
                                )}
                              {row.paidState !== "PENDING" &&
                                row.paidState === "PAID" && (
                                  <Button
                                    onClick={(e) =>
                                      handleOpenCommentDialog(e, row)
                                    }
                                    size="medium"
                                    variant="contained"
                                  >
                                    Comment
                                  </Button>
                                )}
                              {row.paidState === "PENDING" && (
                                <Button
                                  onClick={(e) => handleDecline(e, row)}
                                  size="medium"
                                  variant="contained"
                                  style={{ backgroundColor: "#d66" }}
                                >
                                  Decline
                                </Button>
                              )}
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
      <AcceptDialog
        openAcceptDialog={openAcceptDialog}
        setOpenAcceptDialog={setOpenAcceptDialog}
        request={request}
        setRequest={setRequest}
        requestsInBundle={requestsInBundle}
      ></AcceptDialog>
      <DeclineDialog
        openDeclineDialog={openDeclineDialog}
        setOpenDeclineDialog={setOpenDeclineDialog}
        request={request}
        setRequest={setRequest}
        requestsInBundle={requestsInBundle}
      ></DeclineDialog>
      {/* {openedCommentDialog !== -1 && (
        <CommentDialog
          open={openedCommentDialog !== -1}
          setOpen={setOpenedCommentDialog}
          request={requests.find((r) => r.id == openedCommentDialog)}
        ></CommentDialog>
      )} */}
    </div>
  );
}

export default withRouter(ManipulateRequests);
