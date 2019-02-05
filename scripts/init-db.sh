#!/bin/bash

echo ""
echo "*****"
echo "Creating Transactions DB"
echo "*****"
echo ""

dropdb trans_db --username postgres --host localhost

sudo -u postgres bash -c "psql -c \"DROP ROLE trans_admin;\""
sudo -u postgres bash -c "psql -c \"CREATE ROLE trans_admin LOGIN PASSWORD 'password' SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;\""

sudo -u postgres bash -c "psql -c \"CREATE DATABASE trans_db WITH OWNER = con ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'en_GB.UTF-8' LC_CTYPE = 'en_GB.UTF-8' CONNECTION LIMIT = -1 TEMPLATE template0;\""
sudo -u postgres bash -c "psql -c \"GRANT ALL ON DATABASE trans_db TO trans_admin;\""
#The user "fc" is my default Postgres admin
sudo -u postgres bash -c "psql -c \"GRANT ALL ON DATABASE trans_db TO fc;\""
sudo -u postgres bash -c "psql -c \"REVOKE ALL ON DATABASE trans_db FROM public;\""

psql -U trans_admin -d trans_db -a -f ./1-merchants-ddl.sql
psql -U trans_admin -d trans_db -a -f ./1-transactions-ddl.sql
psql -U trans_admin -d trans_db -a -f ./2-merchants-data.sql
psql -U trans_admin -d trans_db -a -f ./2-transactions-data.sql