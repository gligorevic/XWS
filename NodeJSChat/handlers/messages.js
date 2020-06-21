const db = require("../models");

exports.getAllMessagesByRequestId = async (req, res, next) => {
  const messages = await db.Message.find({ roomId: req.body.roomId });
  console.log(messages);
  console.log(req.body);
  const auth = req.get("Auth");
  console.log("Auth token " + auth);

  res.json(messages);
};

exports.addMessage = async (message) => {
  const newMessage = new db.Message({
    ...message,
  });

  await newMessage.save();
};
