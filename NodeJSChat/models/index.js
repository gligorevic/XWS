const mongoose = require("mongoose");

mongoose.connect(
  process.env.DATABASE_URL || "mongodb://localhost:27017/messages-service",
  {
    useNewUrlParser: true,
    useFindAndModify: false,
    useCreateIndex: true,
  }
);

module.exports.Message = require("./Message");
