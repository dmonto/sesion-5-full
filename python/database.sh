#!/bin/bash
set -e

creating_migration() {
  pipenv run flask db init
  pipenv run flask db migrate
  pipenv run flask db upgrade
}

migrate_upgrade() {
  pipenv run flask db migrate
  pipenv run flask db upgrade
}

dir=$(pwd)

if [ ! -d "$dir/migrations" ]; then
  echo 'Creating migration...'
  creating_migration
else
  echo 'Migrations already created. Updating migrations...'
  migrate_upgrade
fi


