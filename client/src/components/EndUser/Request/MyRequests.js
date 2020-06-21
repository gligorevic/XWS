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
import Checkbox from "@material-ui/core/Checkbox";

import CommentDialog from "../../Dialogs/Comment/CommentDialog";
import GradeDialog from "../../Dialogs/Grade/GradeDialog";

import { getAllPaid } from "../../../store/actions/request";

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

const MyRequests = ({ getAllPaid, allPaidRequests, user }) => {
  useEffect(() => {
    getAllPaid(user.user.username);
  }, []);
  const classes = useStyles();

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const [openCommentDialog, setOpenCommentDialog] = React.useState(false);
  const [request, setRequest] = React.useState(null);
  const [comments, setComments] = React.useState([]);

  const [openGradeDialog, setOpenGradeDialog] = React.useState(false);
  const [grade, setGrade] = React.useState({});

  const handleOpenCommentDialog = async (event, row) => {
    const commentResp = await Axios.get(`/feedback/comment/${row.id}`);

    if (commentResp.status >= 200 && commentResp.status < 300) {
      setRequest(row);
      setComments(commentResp.data);
      setOpenCommentDialog(true);
    }
  };

  const handleOpenGradeDialog = async (event, row) => {
    const gradeResp = await Axios.get(`/feedback/grade/${row.id}`).catch(
      (error) => {
        if (
          error.response &&
          error.response.status === 400 &&
          error.response.data === "No grade for this request"
        ) {
          setOpenGradeDialog(true);
          setRequest(row);
          grade["grade"] = 0;
          grade["requestId"] = row.id;
          grade["username"] = row.userSentRequest;
        }
      }
    );

    if (gradeResp && gradeResp.status >= 200 && gradeResp.status < 300) {
      setRequest(row);
      grade["grade"] = gradeResp.data.grade;
      grade["requestId"] = gradeResp.data.requestId;
      grade["username"] = row.userSentRequest;
      setOpenGradeDialog(true);
    }
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div>
      {allPaidRequests && (
        <Paper className={classes.paper}>
          <div className={classes.tableWrapper}>
            <Table
              className={classes.table}
              aria-labelledby="tableTitle"
              aria-label="enhanced table"
            >
              <TableHead>
                <TableRow>
                  <TableCell align="left">User Sent Request</TableCell>
                  <TableCell align="left">From</TableCell>
                  <TableCell align="left">To</TableCell>
                  <TableCell align="left">STATUS</TableCell>
                  <TableCell align="left">In Bundle</TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {allPaidRequests &&
                  allPaidRequests.length > 0 &&
                  allPaidRequests.map((row, index) => {
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
                            <Button
                              onClick={(e) => handleOpenCommentDialog(e, row)}
                              size="medium"
                              variant="contained"
                            >
                              Comment
                            </Button>
                          </TableCell>
                          <TableCell align="left">
                            <Button
                              onClick={(e) => handleOpenGradeDialog(e, row)}
                              size="medium"
                              variant="contained"
                              style={{ backgroundColor: "#d66" }}
                            >
                              Grade
                            </Button>
                          </TableCell>
                        </TableRow>
                      </>
                    );
                  })}
                {rowsPerPage -
                  Math.min(
                    rowsPerPage,
                    allPaidRequests.length - page * rowsPerPage
                  ) >
                  0 && (
                  <TableRow
                    style={{
                      height:
                        53 *
                        (rowsPerPage -
                          Math.min(
                            rowsPerPage,
                            allPaidRequests.length - page * rowsPerPage
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
            count={allPaidRequests.length}
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

      <CommentDialog
        openCommentDialog={openCommentDialog}
        setOpenCommentDialog={setOpenCommentDialog}
        request={request}
        comments={comments}
        setComments={setComments}
      ></CommentDialog>

      <GradeDialog
        openGradeDialog={openGradeDialog}
        setOpenGradeDialog={setOpenGradeDialog}
        request={request}
        grade={grade}
        setGrade={setGrade}
      ></GradeDialog>
    </div>
  );
};

const mapStateToProps = (state) => ({
  user: state.user,
  allPaidRequests: state.request.allPaidRequests,
});

export default withRouter(
  connect(mapStateToProps, {
    getAllPaid,
  })(MyRequests)
);
