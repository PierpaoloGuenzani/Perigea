#bilding project
FROM node:alpine
WORKDIR /data-viewer/
COPY package*.json .
RUN npm install
COPY . .
RUN npm run build
#RUN npm install serve -g
EXPOSE 3000
CMD ["npm", "start"]