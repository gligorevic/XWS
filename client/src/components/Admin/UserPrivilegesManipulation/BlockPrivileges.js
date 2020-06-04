import React, { useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import List from "@material-ui/core/List";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import Checkbox from "@material-ui/core/Checkbox";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
  root: {
    margin: "auto",
  },
  cardHeader: {
    padding: theme.spacing(1, 2),
  },
  list: {
    width: 350,
    height: 400,
    backgroundColor: theme.palette.background.paper,
    overflow: "auto",
  },
  button: {
    margin: theme.spacing(0.5, 0),
  },
}));

function not(privileges, checked) {
  return privileges.filter((p) => checked.indexOf(p) === -1);
}

function intersection(checked, privileges) {
  return checked.filter((id) => privileges.some((p) => p.id === id));
}

function intersectionPrivileges(checked, privileges) {
  return privileges.filter((p) => checked.indexOf(p.id) !== -1);
}

function union(a, b) {
  return [...a, ...not(b, a)];
}

export default function TransferList({
  allPrivileges,
  blockedPrivileges,
  handleChangeBlockedPrivileges,
}) {
  const classes = useStyles();
  const [checked, setChecked] = React.useState([]);
  const [left, setLeft] = React.useState([]);
  const [right, setRight] = React.useState([]);
  const [privChanged, setPrivChanged] = React.useState(false);

  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  const leftCheckedPrivileges = intersectionPrivileges(checked, left);
  const rightCheckedPrivileges = intersectionPrivileges(checked, right);

  useEffect(() => {
    setLeft(
      allPrivileges.filter(
        (p) => !blockedPrivileges.some((bp) => bp.id === p.id)
      )
    );
    setRight(blockedPrivileges);
  }, [allPrivileges, blockedPrivileges]);

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }
    setChecked(newChecked);
  };

  const numberOfChecked = (items) => intersection(checked, items).length;

  const handleToggleAll = (items) => () => {
    if (numberOfChecked(items) === items.length) {
      setChecked(not(checked, items));
    } else {
      setChecked(union(checked, items));
    }
  };

  const handleCheckedRight = () => {
    setRight(right.concat(leftCheckedPrivileges));
    setLeft(not(left, leftCheckedPrivileges));
    setChecked(not(checked, leftChecked));
  };

  const handleCheckedLeft = () => {
    setLeft(left.concat(rightCheckedPrivileges));
    setRight(not(right, rightCheckedPrivileges));
    setChecked(not(checked, rightChecked));
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setPrivChanged(false);
  };

  const customList = (title, privileges) => (
    <Card>
      <CardHeader
        className={classes.cardHeader}
        avatar={
          <Checkbox
            onClick={handleToggleAll(privileges.map((p) => p.id))}
            checked={
              numberOfChecked(privileges) === privileges.length &&
              privileges.length !== 0
            }
            indeterminate={
              numberOfChecked(privileges) !== privileges.length &&
              numberOfChecked(privileges) !== 0
            }
            disabled={privileges.length === 0}
            inputProps={{ "aria-label": "all privileges selected" }}
          />
        }
        title={title}
        subheader={`${numberOfChecked(privileges)}/${
          privileges.length
        } selected`}
      />
      <Divider />
      <List className={classes.list} dense component="div" role="list">
        {privileges.map((privilege) => {
          const labelId = `transfer-list-all-item-${privilege.id}-label`;

          return (
            <ListItem
              key={privilege.id}
              role="listitem"
              button
              onClick={handleToggle(privilege.id)}
            >
              <ListItemIcon>
                <Checkbox
                  checked={checked.indexOf(privilege.id) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{ "aria-labelledby": labelId }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={privilege.name} />
            </ListItem>
          );
        })}
        <ListItem />
      </List>
    </Card>
  );

  return (
    <Grid
      container
      spacing={2}
      justify="center"
      alignItems="center"
      className={classes.root}
    >
      <Grid item>{customList("Choices", left)}</Grid>
      <Grid item>
        <Grid container direction="column" alignItems="center">
          <Button
            variant="outlined"
            size="small"
            className={classes.button}
            onClick={handleCheckedRight}
            disabled={leftChecked.length === 0}
            aria-label="move selected right"
          >
            &gt;
          </Button>
          <Button
            variant="outlined"
            size="small"
            className={classes.button}
            onClick={handleCheckedLeft}
            disabled={rightChecked.length === 0}
            aria-label="move selected left"
          >
            &lt;
          </Button>
        </Grid>
      </Grid>
      <Grid item>{customList("Chosen", right)}</Grid>
      <Grid
        item
        xs={12}
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Button
          variant="contained"
          color="primary"
          style={{ width: 300, height: 45 }}
          onClick={() => {
            handleChangeBlockedPrivileges(right);
            setPrivChanged(true);
          }}
          disabled={
            right.length === blockedPrivileges.length &&
            right.every((value, index) => value === blockedPrivileges[index])
          }
        >
          Confirm changes
        </Button>
      </Grid>
      <Snackbar
        open={privChanged}
        autoHideDuration={6000}
        onClose={handleClose}
      >
        <Alert onClose={handleClose} severity="success">
          Privileges successfully updated.
        </Alert>
      </Snackbar>
    </Grid>
  );
}
