# autenticacao-stateless-stateful-microsservicos

Projeto para estudo com Java 17, Spring Boot 3, JWT, PostgreSQL, Redis, Docker e Docker-compose.

## Tecnologias

* **Java 17**
* **Spring Boot 3**
* **API REST**
* **PostgreSQL (Container)**
* **Docker**
* **docker-compose**
* **JWT**

## Docker

Para subir o projeto, usar os comandos:

- `start.sh` - Iniciar os containers;
- `stop.sh` - Parar todos os containers;
- `remover.sh` - Para e remover os containers;
- `remove_volumes.sh` - Para, remover e deletar volumes dos containers;
- `exec_redis.sh` - Entrar no container do Radis.

Para subir apenas um container: `docker compose up --build -d stateless-auth-db`

## Chave pública e privada

Para criar as chaves públicas e privadas, executar a shell: `criar_chaves.sh`. As aplicações já tem, no application.yml, as chaves, mas se você quiser, podem gerar novas com esse comando.

### Execução das aplicações manualmente

Para rodar as aplicações, será necessário ter instalado:

* Docker
* Java 17
* Gradle 7.6 ou superior

Para rodar as aplicações, você pode rodar diretamente via IDE, ou também, pode executar o comando: `mvn spring-boot:run` na raiz de cada aplicação. Realizar o build antes.

## Atualizar build e artefatos dos containers

- `build.py` - Criar os artefatos e build em todos os containers.

Para rodar tudo com o script, basta executar o comando:

`python build.py`

Para isso, será necessário ter o Python 3 instalado.

O script fará todos os seguintes processos:

* Parará e apagará todos os containers rodando.
* Entrará no diretório de cada uma das 4 APIs e rodará o comando `mvn package` em paralelo para não levar muito tempo realizando sequencialmente.
* Irá aguardar o build de cada API finalizar.
* Assim que todos os builds finalizarem, então irá executar todos os containers novamente.

## Aplicações

Os acessos de host e porta dos bancos de dados são:

* stateless-auth-db (PostgreSQL): localhost:5432
* stateful-auth-db (PostgreSQL): localhost:5433
* token-redis (Redis): localhost:6379


## SpotBugs

SpotBugs é um programa que usa análise estática para procurar bugs no código Java. É um software livre, distribuído sob os termos da Licença Pública Geral Menor GNU .

SpotBugs é um fork do FindBugs (que agora é um projeto abandonado), continuando do ponto onde parou com o apoio de sua comunidade. Por favor, verifique o manual oficial para obter detalhes.

SpotBugs requer JRE (ou JDK) 1.8.0 ou posterior para ser executado. No entanto, pode analisar programas compilados para qualquer versão do Java.

[Tutorial](https://spotbugs.readthedocs.io/en/latest/maven.html)

Para verificar usando o SpotBugs: `mvn spotbugs:check`

