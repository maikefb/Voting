# Voting
API para criar pautas de votação e fazer o processamento da mesma

## Swagger 
- [Swagger Local](http://localhost:8020/voting-api/swagger-ui.html)

## Requisitos para rodar local a aplicação
Aplicação roda atualmente com Java 17

Para verificar se seu IntelliJ IDE está rodando com a 17 do Java, você pode acessar `File > Project Structure` onde abrirá uma modal e você poderá verificar se está configurada a SDK 17 do Java e a language level do Java 17.

### Banco Local

Aplicação utiliza banco MySQL local, recomendamos o uso do [Docker](https://www.docker.com/get-started) para gerenciarmos containers de imagens de banco de dados local. Já com o Docker instalado, deve ser rodado o comando abaixo para criarmos um container com imagem MySQL, requisito para subir a aplicação localmente.

```
docker run -p 3306:3306 --name db_voting -e MYSQL_ROOT_PASSWORD=toor -e MYSQL_DATABASE=db_voting mysql:8.0.20
```

Em seu workbench, você pode acessar o banco local criado com os seguintes acessos:

```
- Database: db_voting
- Port: 3306
- Username: root
- Password: toor
```

Workbench recomendados: [DBeaver](https://dbeaver.io/), [MySQL WorkBench](https://www.mysql.com/products/workbench/), [DataGrip](https://www.jetbrains.com/pt-br/datagrip/).

### IDE

Recomendamos o uso do [IntelliJ IDE](https://www.jetbrains.com/pt-br/idea/download/) como IDE utilizada.

### Plugins

Já com o [IntelliJ IDE](https://www.jetbrains.com/pt-br/idea/download/) instalado, devemos instalar também o [Plugin Lombok](https://plugins.jetbrains.com/plugin/6317-lombok), em seu própio IntelliJ você pode acessar o Marketplace de plugin na aba `Preferências > Plugin > Marketplace` pesquisando por "Lombok".

## Configurações
Pode ser modificado o caminho base (context-path) e a porta pelo application.yml