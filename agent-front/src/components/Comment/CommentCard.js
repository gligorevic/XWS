import React, { useState } from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardActions from "@material-ui/core/CardActions";
import { withRouter } from "react-router";
import Typography from "@material-ui/core/Typography";
import CardContent from "@material-ui/core/CardContent";
import { format } from "date-fns";
import IconButton from "@material-ui/core/IconButton";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import clsx from "clsx";
import { makeStyles } from "@material-ui/core/styles";
import Collapse from "@material-ui/core/Collapse";
import CommentReply from "./CommentReply";
import { connect } from "react-redux";

const useStyles = makeStyles((theme) => ({
  expand: {
    transform: "rotate(0deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: "rotate(180deg)",
  },
}));

const CommentCard = ({ comment, user }) => {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  return (
    <Card raised style={{ width: 450 }}>
      <CardHeader
        title={`${comment.userEmail} `}
        subheader={format(new Date(comment.creationDate), "dd MMM yyyy")}
      />
      <CardContent>
        <Typography variant="body2" component="p">
          {comment.text}
        </Typography>
      </CardContent>
      <CardActions disableSpacing className="cardFooter">
        {user.role && user.role.some((r) => r.name === "ROLE_AGENT") && (
          <IconButton
            className={clsx(classes.expand, {
              [classes.expandOpen]: expanded,
            })}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </IconButton>
        )}
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <CommentReply requestId={comment.requestId} />
        </CardContent>
      </Collapse>
    </Card>
  );
};

const mapStateToProps = (state) => ({
  user: state.user.user,
});

export default connect(mapStateToProps, {})(CommentCard);
