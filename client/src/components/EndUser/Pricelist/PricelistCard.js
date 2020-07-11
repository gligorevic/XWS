import React, { useState } from "react";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardActions from "@material-ui/core/CardActions";
import AddAdvertisement from "../AddAdvertisement";
import AddCommentIcon from "@material-ui/icons/AddComment";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import { format } from "date-fns";
import { withRouter } from "react-router";

function PricelistCard({ pricelist, history, userEmail }) {
  const [openActiveAd, setOpenAcitveAd] = useState(-1);

  const handleSetOpen = (openState) => {
    if (!openState) {
      setOpenAcitveAd(-1);
    }
  };

  return (
    <Card raised style={{ width: 200 }}>
      <CardHeader
        title={`${format(
          new Date(pricelist.validFrom),
          "dd MMM yyyy"
        )} - ${format(new Date(pricelist.validTo), "dd MMM yyyy")}`}
        subheader={userEmail}
      />

      <CardActions disableSpacing className="cardFooter">
        <IconButton>
          <AddCommentIcon />
        </IconButton>

        <Button
          size="small"
          color="primary"
          onClick={() => history.push(`/pricelist/${pricelist.id}`)}
        >
          View pricelist
        </Button>
      </CardActions>
    </Card>
  );
}

export default withRouter(PricelistCard);
