FROM node:14-alpine

COPY package.json .

RUN npm install

COPY . .

EXPOSE 3200

CMD [ "node", "index" ]