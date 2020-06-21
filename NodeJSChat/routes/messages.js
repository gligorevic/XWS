const express = require("express");
const router = express.Router();

const { getAllMessagesByRequestId } = require("../handlers/messages");

router.post("/", getAllMessagesByRequestId);

module.exports = router;
