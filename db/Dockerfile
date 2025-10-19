# Use the latest MySQL image
FROM mysql
# Set the working directory
# Copy all the files to the working directory of the container
COPY test_db/*.sql /tmp/
# Copy the main SQL file to docker-entrypoint-initdb.d.
# Scripts and SQL files in this folder are executed on container startup.
# This is specific to MySQL.
COPY test_db/*.sql /docker-entrypoint-initdb.d/
