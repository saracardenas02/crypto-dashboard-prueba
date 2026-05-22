FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
ARG QUERY_URL=http://localhost:8080/items
ARG MANAGEMENT_URL=http://localhost:8082/watchlist
RUN echo "export const environment = { production: true, queryServiceUrl: '${QUERY_URL}', managementServiceUrl: '${MANAGEMENT_URL}' };" > src/environments/environment.prod.ts
RUN npx ng build --configuration=production

FROM nginx:alpine
COPY --from=builder /app/dist/crypto-frontend/browser /usr/share/nginx/html
RUN echo "server { listen 80; root /usr/share/nginx/html; index index.html; location / { try_files \$uri \$uri/ /index.html; } }" > /etc/nginx/conf.d/default.conf
EXPOSE 80