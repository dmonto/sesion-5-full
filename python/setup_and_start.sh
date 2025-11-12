#!/bin/bash
set -e

if [ ! -f .env ]; then
  echo "ERROR: Archivo .env no encontrado. Por favor crea un .env con la configuración proporcionada."
  exit 1
fi

echo "Ejecutando migraciones y actualizaciones en base de datos..."
pipenv run flask db init || true
pipenv run flask db migrate
pipenv run flask db upgrade

# echo "Insertando usuarios de prueba..."
# pipenv run flask insert-test-users 5

echo "Instalando dependencias npm..."
pipenv run npm install

echo "Iniciando app React"
pipenv run start &                 # Lanzar la app React (frontend)

echo "Iniciando Backend"
pipenv run gunicorn --bind 0.0.0.0:5000 src.app:app   # Backend
