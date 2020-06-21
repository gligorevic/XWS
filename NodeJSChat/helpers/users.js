const users = [];

const addUser = ({ id, name, room, recieverEmail, chatName }) => {
  name = name.trim().toLowerCase();
  room = room.trim().toLowerCase();

  const existingUser = users.find(
    (user) => user.room === room && user.name === name
  );

  if (!name || !room) return { error: "Username and room are required." };

  const user = { id, name, room, recieverEmail, chatName };

  if (existingUser) return { user };

  users.push(user);

  return { user };
};

const removeUser = ({ roomId, username }) => {
  const index = users.findIndex(
    (user) => user.room === roomId && user.name === username
  );
  console.log("Index za brisku" + index);
  if (index !== -1) return users.splice(index, 1)[0];
};

const getUserByRoomIdAndUsername = (room, name) => {
  console.log(users);
  return users.find((user) => user.room === room && user.name === name);
};

const getUserByRecieverEmail = (recieverEmail) => {
  return users.filter((u) => u.recieverEmail === recieverEmail);
};

const getUsersInRoom = (room) => users.filter((user) => user.room === room);

module.exports = {
  addUser,
  removeUser,
  getUsersInRoom,
  getUserByRecieverEmail,
  getUserByRoomIdAndUsername,
};
