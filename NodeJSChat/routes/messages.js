const express = require("express");
const router = express.Router();
const { hasPermision } = require("../middleware/auth");
const { getAllMessagesByRequestId } = require("../handlers/messages");

router.post("/", hasPermision("MESSAGE_VIEWING"), getAllMessagesByRequestId);

module.exports = router;
