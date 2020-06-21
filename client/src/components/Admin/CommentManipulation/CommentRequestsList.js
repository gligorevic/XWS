import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router";
import Axios from "axios";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import { Paper, TableCell } from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import { getAllComments } from "../../../store/actions/comment";

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

const CommentRequestsList = ({ allComments, getAllComments }) => {
  useEffect(() => {
    getAllComments();
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

  const handleAcceptComment = async (event, row) => {
    event.preventDefault();
    row.commentStatus = "ACCEPTED";
    const res = await Axios.put(`/feedback/comment/${row.id}`, row);

    if (res.status >= 200 && res.status < 300) {
      getAllComments();
    }
  };

  const handleDeclineComment = async (event, row) => {
    event.preventDefault();
    row.commentStatus = "REJECTED";
    const res = await Axios.put(`/feedback/comment/${row.id}`, row);

    if (res.status >= 200 && res.status < 300) {
      getAllComments();
    }
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
      {allComments && (
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
                      active={orderBy === "userSentComment"}
                      direction={order}
                      onClick={() => handleRequestSort("userSentComment")}
                    >
                      User Sent Comment
                    </TableSortLabel>
                  </TableCell>
                  <TableCell align="left">Request id</TableCell>
                  <TableCell align="left">Text</TableCell>
                  <TableCell align="left">
                    <TableSortLabel
                      active={orderBy === "commentState"}
                      direction={order}
                      onClick={() => handleRequestSort("commentState")}
                    >
                      STATUS
                    </TableSortLabel>
                  </TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {allComments &&
                  allComments.length > 0 &&
                  sort(allComments)
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
                              {row.username}
                            </TableCell>
                            <TableCell align="left">{row.requestId}</TableCell>
                            <TableCell align="left">{row.text}</TableCell>

                            <TableCell align="left">
                              {row.commentStatus}
                            </TableCell>

                            <TableCell align="left">
                              <Button
                                onClick={(e) => handleAcceptComment(e, row)}
                                size="medium"
                                variant="contained"
                                color="primary"
                              >
                                Accept
                              </Button>
                            </TableCell>
                            <TableCell align="left">
                              <Button
                                onClick={(e) => handleDeclineComment(e, row)}
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
                  Math.min(
                    rowsPerPage,
                    allComments.length - page * rowsPerPage
                  ) >
                  0 && (
                  <TableRow
                    style={{
                      height:
                        53 *
                        (rowsPerPage -
                          Math.min(
                            rowsPerPage,
                            allComments.length - page * rowsPerPage
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
            count={allComments.length}
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
    </div>
  );
};

const mapStateToProps = (state) => ({
  allComments: state.comment.allComments,
});

export default withRouter(
  connect(mapStateToProps, {
    getAllComments,
  })(CommentRequestsList)
);
