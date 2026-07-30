# Agent Context

## Execution Environment

- The environment is running **BusyBox**, which provides a lightweight version of common Unix tools.
- The `pgrep` command is a "stripped down" version and **does not support the `-g` flag**.
- The `gh` (GitHub CLI) tool is available if needed.

## Project Overview

This is the `petclinic`. It contains a shared PostgreSQL database foundation for the Petclinic domain, Dockerized with official `postgres:18-alpine` and managed via Flyway migrations. The `docker-compose.yml` provides `postgres`, `flyway`, and `pgadmin` services. Child repos `petclinic-*` are expected to consume this database as their persistence layer.
