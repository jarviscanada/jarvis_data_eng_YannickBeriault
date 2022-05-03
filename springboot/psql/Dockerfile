FROM postgres:9.6-alpine
ADD sql_ddl/init_db.sql /docker-entrypoint-initdb.d/
ADD sql_ddl/schema.sql /docker-entrypoint-initdb.d/