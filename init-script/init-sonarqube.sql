-- Create the sonar database and user
CREATE DATABASE sonar;
\c sonar;

-- Create extension if not exists
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Grant all privileges to sonar user
GRANT ALL PRIVILEGES ON DATABASE sonar TO sonar;