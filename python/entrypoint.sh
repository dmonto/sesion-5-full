#!/bin/bash
set -e

# Instala las dependencias del proyecto desde el Pipfile.lock
echo "Instalando dependencias del backend..."
pipenv install --deploy --ignore-pipfile

echo "Dependencias instaladas."

echo "Ejecutando script de base de datos..."

# Espera a que la base de datos esté lista si es necesario (opcional pero recomendado)
# Por ejemplo: while ! nc -z db 5432; do sleep 1; done;

# Ejecuta las migraciones usando 'pipenv run' para asegurar el contexto correcto
echo "Actualizando migraciones..."
pipenv run flask db upgrade

echo "Migraciones completadas."
exec /python/setup_and_start.sh

# Ejecuta el comando principal (CMD de Dockerfile)
echo "Iniciando la aplicación Flask..."
exec "$@"
