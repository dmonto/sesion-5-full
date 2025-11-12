FROM node:20-slim

WORKDIR /frontend

COPY package*.json ./

RUN npm install --include=dev

COPY . .

EXPOSE 3000

CMD ["npm", "start"]
