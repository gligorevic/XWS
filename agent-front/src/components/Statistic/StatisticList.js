import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import { format } from "date-fns";
import { withRouter } from "react-router";
import { connect } from "react-redux";
import Typography from "@material-ui/core/Typography";
import Rating from "@material-ui/lab/Rating";
import TableSortLabel from "@material-ui/core/TableSortLabel";

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

const StatisticList = ({ statistic }) => {
  const classes = useStyles();

  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("averageGrade");
  const sort = (cars) => {
    return cars.sort((a, b) => {
      if (orderBy === "averageGrade") {
        return order === "asc"
          ? a.averageGrade < b.averageGrade
            ? 1
            : -1
          : b.averageGrade < a.averageGrade
          ? 1
          : -1;
      } else if (orderBy === "numberOfComments") {
        return order === "asc"
          ? a.numberOfComments < b.numberOfComments
            ? 1
            : -1
          : b.numberOfComments < a.numberOfComments
          ? 1
          : -1;
      } else {
        return order === "asc" ? (a.km < b.km ? 1 : -1) : b.km < a.km ? 1 : -1;
      }
    });
  };

  const handleCarsSort = (property, event) => {
    const isDesc = orderBy === property && order === "desc";
    setOrder(isDesc ? "asc" : "desc");
    setOrderBy(property);
  };
  return (
    <React.Fragment>
      <Typography component="h2" variant="h6" color="secondary" gutterBottom>
        Cars statistic
      </Typography>
      {statistic && (
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>Brand - Model</TableCell>
              <TableCell>
                <TableSortLabel
                  active={orderBy === "km"}
                  direction={order}
                  onClick={() => handleCarsSort("km")}
                >
                  Km passed
                </TableSortLabel>
              </TableCell>
              <TableCell>
                <TableSortLabel
                  active={orderBy === "averageGrade"}
                  direction={order}
                  onClick={() => handleCarsSort("averageGrade")}
                >
                  Average grade
                </TableSortLabel>
              </TableCell>
              <TableCell>
                <TableSortLabel
                  active={orderBy === "numberOfComments"}
                  direction={order}
                  onClick={() => handleCarsSort("numberOfComments")}
                >
                  Number of comments
                </TableSortLabel>
              </TableCell>
              <TableCell align="right">Created on</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {statistic &&
              sort(statistic.cars).map((row) => (
                <TableRow key={row.car.id}>
                  <TableCell>
                    {row.car.brandName + ` - ` + row.car.modelName}
                  </TableCell>
                  <TableCell>{row.km}</TableCell>
                  <TableCell>
                    <Rating
                      name="read-only"
                      value={row.averageGrade}
                      precision={0.25}
                      readOnly
                    />
                  </TableCell>
                  <TableCell>{row.numberOfComments}</TableCell>
                  <TableCell align="right">
                    {`on ` +
                      format(new Date(row.car.creationDate), "dd MMM yyyy")}
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      )}
      <div className={classes.seeMore}></div>
    </React.Fragment>
  );
};

const mapStateToProps = (state) => ({});

export default withRouter(connect(mapStateToProps, {})(StatisticList));
