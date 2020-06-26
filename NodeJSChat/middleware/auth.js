let jwt = require("jsonwebtoken");

exports.hasPermision = (perm) => (req, res, next) => {
  try {
    const token = req.get("Auth").split(" ")[1].toString();
    console.log(token);
    const decoded = jwt.decode(token);
    console.log(decoded);
    if (decoded) {
      if (decoded.privileges.includes(perm)) return next();
      else throw new Error("Premision not found");
    }
  } catch (err) {
    console.log(err);
    return next({ status: 401, message: "Unauthorized" });
  }
};
