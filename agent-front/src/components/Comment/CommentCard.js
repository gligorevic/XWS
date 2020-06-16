import React, { useState } from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardActions from "@material-ui/core/CardActions";
import { withRouter } from "react-router";
import Typography from "@material-ui/core/Typography";
import CardContent from "@material-ui/core/CardContent";
import { format } from "date-fns";

function CommentCard({ comment }) {
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
      <CardActions disableSpacing className="cardFooter"></CardActions>
    </Card>
  );
}

export default withRouter(CommentCard);
