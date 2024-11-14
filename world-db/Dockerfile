# Use the MySQL image version 8.3 (allows native password plugin)
FROM mysql:8.3
WORKDIR /tmp
# This is specific to MySQL.
COPY ./world.sql/*.sql /tmp/
COPY ./world.sql/*.dump /tmp/
COPY ./world.sql /docker-entrypoint-initdb.d
# Set the root password
ENV MYSQL_ROOT_PASSWORD example
#Expose the ports 3306
EXPOSE 3306:3306