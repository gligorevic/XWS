let users = [];

const addUser = (user) => {
  users.push(user);
};

const userExists = (user) => {
  return users.some((u) => user === u);
};

const deleteUser = (user) => {
  users = users.filter((u) => u !== user);
};

module.exports = {
  addUser,
  userExists,
  deleteUser,
};
