import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import SpeedDial from "@material-ui/lab/SpeedDial";
import SpeedDialIcon from "@material-ui/lab/SpeedDialIcon";
import SpeedDialAction from "@material-ui/lab/SpeedDialAction";
import GradeIcon from "@material-ui/icons/Grade";
import CancelIcon from "@material-ui/icons/Cancel";
import QuestionAnswerIcon from "@material-ui/icons/QuestionAnswer";
import ChatIcon from "@material-ui/icons/Chat";
import { connect } from "react-redux";
import { initializeNewChatBox } from "../../../../store/actions/chat";
import {
  payRequest,
  cancelRequest,
  getCreatedRequests,
} from "../../../../store/actions/request";
import MonetizationOnIcon from "@material-ui/icons/MonetizationOn";

const useStyles = makeStyles((theme) => ({
  positionAps: {
    position: "absolute",
    right: 10,
    bottom: 5,
    zIndex: 10,
  },
}));

const actions = [
  { icon: <QuestionAnswerIcon />, name: "Chat" },
  { icon: <ChatIcon />, name: "Comment" },
  { icon: <GradeIcon />, name: "Rate" },
  { icon: <MonetizationOnIcon />, name: "Pay" },
  { icon: <CancelIcon />, name: "Cancel" },
];

function RequestActions({
  visibility,
  chatName,
  roomId,
  openChatBoxes,
  initializeNewChatBox,
  sendTo,
  setOpenedDialog,
  payRequest,
  cancelRequest,
  show,
  readOnly,
  getCreatedRequests,
  user,
}) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleAction = async (name) => {
    switch (name) {
      case "Chat":
        if (!openChatBoxes.some((o) => o.chatName === chatName))
          initializeNewChatBox({
            chatName,
            roomId,
            sendTo,
            unreadedMessages: 0,
            messages: [],
            readOnly,
          });
        break;
      case "Rate":
        setOpenedDialog(roomId);
        break;
      case "Comment":
        setOpenedDialog("c" + roomId);
        break;
      case "Pay":
        await payRequest(roomId);
        getCreatedRequests(user.username);
        break;
      case "Cancel":
        cancelRequest(roomId);
        break;
    }

    setOpen(false);
  };

  return (
    <SpeedDial
      ariaLabel="SpeedDial example"
      className={classes.positionAps}
      icon={<SpeedDialIcon />}
      onClose={handleClose}
      onOpen={handleOpen}
      open={open}
      direction="up"
      style={{
        visibility: visibility ? "visible" : "hidden",
      }}
    >
      {actions.map(
        (action) =>
          show.includes(action.name) && (
            <SpeedDialAction
              key={action.name}
              icon={action.icon}
              tooltipTitle={action.name}
              onClick={() => handleAction(action.name)}
            />
          )
      )}
    </SpeedDial>
  );
}

const mapStateToProps = (state) => ({
  openChatBoxes: state.chat.openChatBoxes,
  user: state.user.user,
});

export default connect(mapStateToProps, {
  initializeNewChatBox,
  payRequest,
  cancelRequest,
  getCreatedRequests,
})(RequestActions);
