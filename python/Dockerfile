FROM python:3.13-slim

WORKDIR /python

# Instalar herramientas necesarias y Node (para npm)
RUN apt-get update && apt-get install -y bash curl git nodejs npm

# Copiar Pipfile para instalación pipenv
COPY Pipfile Pipfile.lock ./

RUN pip install --no-cache-dir pipenv
RUN pipenv install --dev --deploy --ignore-pipfile

COPY . .

EXPOSE 3001 5678

# Ejecutar flask con debugpy para depuración remota
CMD ["env", "FLASK_APP=src.app", "python", "-Xfrozen_modules=off", "-m", "debugpy", "--listen", "0.0.0.0:5678", "-m", "flask", "run", "--host=0.0.0.0"]
