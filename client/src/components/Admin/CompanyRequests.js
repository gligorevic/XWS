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

import { getAllCompanyRequests } from "../../store/actions/user";

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

const CompanyRequests = ({ getAllCompanyRequests, companyRequests }) => {
  useEffect(() => {
    getAllCompanyRequests();
  }, []);
  const classes = useStyles();

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleAcceptCompanyRequest = async (e, row) => {
    const resp = await Axios.put(`auth/company/${row.id}/accept`);
    getAllCompanyRequests();
  };

  const handleDeclineCompanyRequest = async (e, row) => {
    const resp = await Axios.put(`auth/company/${row.id}/decline`);
    getAllCompanyRequests();
  };

  return (
    <div>
      {companyRequests && (
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
                  <TableCell align="left">Company Name</TableCell>
                  <TableCell align="left">Registration</TableCell>
                  <TableCell align="left">Phone Number</TableCell>
                  <TableCell align="left">Address</TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {companyRequests &&
                  companyRequests.length > 0 &&
                  companyRequests.map((row, index) => {
                    return (
                      <>
                        <TableRow
                          hover
                          role="checkbox"
                          tabIndex={-1}
                          key={row.user.email}
                        >
                          <TableCell component="th" allign="left">
                            {row.user.email}
                          </TableCell>
                          <TableCell align="left">{row.companyName}</TableCell>
                          <TableCell align="left">
                            {row.reqistrationNumber}
                          </TableCell>

                          <TableCell align="left">{row.phoneNumber}</TableCell>
                          <TableCell align="left">{row.address}</TableCell>
                          <TableCell align="left">
                            <Button
                              onClick={(e) =>
                                handleAcceptCompanyRequest(e, row)
                              }
                              color="primary"
                              size="medium"
                              variant="contained"
                            >
                              Accept
                            </Button>
                          </TableCell>
                          <TableCell align="left">
                            <Button
                              onClick={(e) =>
                                handleDeclineCompanyRequest(e, row)
                              }
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
                    companyRequests.length - page * rowsPerPage
                  ) >
                  0 && (
                  <TableRow
                    style={{
                      height:
                        53 *
                        (rowsPerPage -
                          Math.min(
                            rowsPerPage,
                            companyRequests.length - page * rowsPerPage
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
            count={companyRequests.length}
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
  user: state.user,
  companyRequests: state.user.companyRequests,
});

export default withRouter(
  connect(mapStateToProps, {
    getAllCompanyRequests,
  })(CompanyRequests)
);
