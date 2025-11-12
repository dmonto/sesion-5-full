FROM python:3.13-slim

WORKDIR /python

# Instalar herramientas necesarias y Node (para npm)
RUN apt-get update && apt-get install -y bash curl git nodejs npm

# Copiar Pipfile para instalación pipenv
COPY Pipfile Pipfile.lock ./

RUN pip install --no-cache-dir pipenv
RUN pipenv install --deploy

COPY . .

EXPOSE 3001

CMD ["pipenv", "run", "gunicorn", "--bind", "0.0.0.0:3001", "src.app:app"]
