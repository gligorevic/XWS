import React, { useEffect } from "react";
import clsx from "clsx";
import { lighten, makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Switch from "@material-ui/core/Switch";
import { getAllUsers, toggleBlock, deleteUser } from "../../store/actions/user";
import { connect } from "react-redux";
import BlockIcon from "@material-ui/icons/Block";
import AutorenewIcon from "@material-ui/icons/Autorenew";
import DeleteIcon from "@material-ui/icons/Delete";
import "./UserSelection.css";
import Tooltip from "@material-ui/core/Tooltip";

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

function EnhancedTableHead(props) {
  const { order, orderBy, onRequestSort } = props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        <TableCell sortDirection={orderBy === true ? order : false}>
          <TableSortLabel
            active={orderBy === true}
            direction={orderBy === true ? order : "asc"}
            onClick={createSortHandler(true)}
          >
            Username
          </TableSortLabel>
        </TableCell>
        <TableCell
          align="right"
          sortDirection={orderBy === true ? order : false}
        >
          <TableSortLabel
            active={orderBy === true}
            direction={orderBy === true ? order : "asc"}
            onClick={createSortHandler(true)}
          >
            First name
          </TableSortLabel>
        </TableCell>
        <TableCell
          align="right"
          sortDirection={orderBy === true ? order : false}
        >
          <TableSortLabel
            active={orderBy === true}
            direction={orderBy === true ? order : "asc"}
            onClick={createSortHandler(true)}
          >
            Last name
          </TableSortLabel>
        </TableCell>
        <TableCell
          align="right"
          sortDirection={orderBy === true ? order : false}
        >
          <TableSortLabel
            active={orderBy === true}
            direction={orderBy === true ? order : "asc"}
            onClick={createSortHandler(true)}
          >
            Un/Block
          </TableSortLabel>
        </TableCell>
      </TableRow>
    </TableHead>
  );
}

const useToolbarStyles = makeStyles((theme) => ({
  root: {
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(1),
  },
  highlight:
    theme.palette.type === "light"
      ? {
          color: theme.palette.secondary.main,
          backgroundColor: lighten(theme.palette.secondary.light, 0.85),
        }
      : {
          color: theme.palette.text.primary,
          backgroundColor: theme.palette.secondary.dark,
        },
  title: {
    flex: "1 1 100%",
  },
}));

const EnhancedTableToolbar = ({
  isSelected,
  user,
  deleteUser,
  allowDelete,
}) => {
  const classes = useToolbarStyles();

  return (
    <Toolbar
      className={clsx(classes.root, {
        [classes.highlight]: isSelected,
      })}
      style={{ background: "#fafafa" }}
    >
      {isSelected ? (
        <Typography
          className={classes.title}
          color="inherit"
          variant="subtitle1"
          component="div"
        >
          <span>
            User <strong>{user.email}</strong> is selected
          </span>
          {allowDelete && (
            <Tooltip title="Delete user">
              <DeleteIcon className="delete" onClick={deleteUser} />
            </Tooltip>
          )}
        </Typography>
      ) : (
        <Typography
          className={classes.title}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          Users
        </Typography>
      )}
    </Toolbar>
  );
};

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
  paper: {
    width: "100%",
    marginBottom: theme.spacing(2),
  },
  table: {
    minWidth: 550,
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
}));

const EnhancedTable = ({
  getAllUsers,
  toggleBlock,
  allUsers,
  currentUser,
  selected,
  handleClick,
  deleteUser,
  setSelected,
}) => {
  const classes = useStyles();
  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("calories");

  const [page, setPage] = React.useState(0);
  const [dense, setDense] = React.useState(false);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [toggling, setToggling] = React.useState(false);

  useEffect(() => {
    (async () => {
      await getAllUsers();
    })();
  }, []);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeDense = (event) => {
    setDense(event.target.checked);
  };

  const isSelected = (id) => selected !== null && selected.id === id;

  const toggleBlockUser = async (e, userId) => {
    e.stopPropagation();
    setToggling(userId);
    await toggleBlock(userId);
    setToggling(false);
  };

  const deleteSelectedUser = async (e) => {
    setSelected(null);
    if (selected) {
      await deleteUser(selected.id);
    }
  };

  const emptyRows =
    rowsPerPage - Math.min(rowsPerPage, allUsers.length - page * rowsPerPage);

  return (
    <div className={classes.root}>
      <Paper className={classes.paper}>
        <EnhancedTableToolbar
          isSelected={selected != null}
          user={selected}
          deleteUser={deleteSelectedUser}
          allowDelete={selected && selected.id != currentUser.id}
        />
        <TableContainer>
          <Table
            className={classes.table}
            aria-labelledby="tableTitle"
            size={dense ? "small" : "medium"}
            aria-label="enhanced table"
          >
            <EnhancedTableHead
              classes={classes}
              order={order}
              orderBy={orderBy}
              onRequestSort={handleRequestSort}
            />
            <TableBody>
              {allUsers
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((user, index) => {
                  const isItemSelected = isSelected(user.id);
                  const labelId = `enhanced-table-checkbox-${index}`;

                  return (
                    <TableRow
                      hover
                      onClick={(event) =>
                        handleClick(event, user, user.id == currentUser.id)
                      }
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={user.id}
                      selected={isItemSelected}
                      style={{
                        background: user.id == currentUser.id && "#c5e1a5",
                      }}
                    >
                      <TableCell
                        component="th"
                        id={labelId}
                        scope="row"
                        padding="none"
                      >
                        {`${user.email} ${
                          user.id == currentUser.id ? "(Me)" : ""
                        }`}
                      </TableCell>
                      <TableCell align="right">{user.firstName}</TableCell>
                      <TableCell align="right">{user.lastName}</TableCell>
                      <TableCell align="right">
                        {user.id != currentUser.id &&
                          (user.blocked ? (
                            <Tooltip title="Unlock user">
                              <AutorenewIcon
                                className={toggling === user.id && "pulsing"}
                                style={{ color: "#4caf50", cursor: "pointer" }}
                                onClick={(e) => toggleBlockUser(e, user.id)}
                                disabled
                              />
                            </Tooltip>
                          ) : (
                            <Tooltip title="Block user">
                              <BlockIcon
                                className={toggling === user.id && "pulsing"}
                                style={{ color: "#f44336", cursor: "pointer" }}
                                onClick={(e) => toggleBlockUser(e, user.id)}
                              />
                            </Tooltip>
                          ))}
                      </TableCell>
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow style={{ height: (dense ? 33 : 53) * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={allUsers.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />
      </Paper>
      <FormControlLabel
        control={<Switch checked={dense} onChange={handleChangeDense} />}
        label="Dense padding"
      />
    </div>
  );
};

const mapStateToProps = (state) => ({
  allUsers: state.user.allUsers,
  currentUser: state.user.user,
});

export default connect(mapStateToProps, {
  getAllUsers,
  toggleBlock,
  deleteUser,
})(EnhancedTable);
