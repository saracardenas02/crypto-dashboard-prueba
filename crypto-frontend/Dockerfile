# Etapa 1: BUILD Angular
FROM node:22-alpine AS builder
WORKDIR /app

# Se copia package.json primero para cachear npm install
COPY package*.json .
RUN npm ci

# Luego se copia el resto y se compila para produccion
COPY . .
RUN npx ng build --configuration=production

# Etapa 2: SERVE con Nginx
FROM nginx:alpine

# Se copia la build de Angular al directorio de Nginx
COPY --from=builder /app/dist/crypto-frontend/browser /usr/share/nginx/html

# Configuracion de Nginx para que Angular routing funcione
# Sin esto, al refrescar /watchlist Nginx devuelve 404
RUN echo "server { \
    listen 80; \
    root /usr/share/nginx/html; \
    index index.html; \
    location / { \
        try_files \$uri \$uri/ /index.html; \
    } \
}" > /etc/nginx/conf.d/default.conf

EXPOSE 80
