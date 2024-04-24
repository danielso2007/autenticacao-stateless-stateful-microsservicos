#!/bin/bash
RED='\033[1;31m'
BLACK='\033[0;30m'
DARK_GRAY='\033[1;30m'
LIGHT_RED='\033[0;31m'
GREEN='\033[0;32m'
LIGHT_GREEN='\033[1;32m'
BROWN_ORANGE='\033[0;33m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
LIGHT_BLUE='\033[1;34m'
PURPLE='\033[0;35m'
LIGHT_PURPLE='\033[1;35m'
CYAN='\033[0;36m'
LIGHT_CYAN='\033[1;36m'
LIGHT_GRAY='\033[0;37m'
WHITE='\033[1;37m'
NC='\033[0m' # No Color
echo -e "${YELLOW}Parando container...${NC}"
docker compose stop
docker compose ps
echo -e "${YELLOW}Removendo container...${NC}"
docker compose rm -f
echo -e "${YELLOW}Removendo volumes ...${NC}"
docker volume rm autenticacao-stateless-stateful-microsservicos_pgsqlconfStateless autenticacao-stateless-stateful-microsservicos_pgsqldataStateless autenticacao-stateless-stateful-microsservicos_pgsqldblogsStateless autenticacao-stateless-stateful-microsservicos_pgsqlconfStateful autenticacao-stateless-stateful-microsservicos_pgsqldataStateful autenticacao-stateless-stateful-microsservicos_pgsqldblogsStateful autenticacao-stateless-stateful-microsservicos_redisToken
docker network rm auth
docker volume ls
docker network ls