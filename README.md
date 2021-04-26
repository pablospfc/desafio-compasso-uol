## Sobre a API

Este projeto é uma API desenvolvida como parte do teste para Desenvolvedor Java Spring Boot da Compasso UOL. É uma API para catálogo de produtos. Foi desenvolvida utilizando Java com Spring Boot.

## Funcionalidades

Os endpoints providos por esta API são:

* Criar um novo produto: `POST /products`
* Atualizar um produto na API: `PUT /products/:id`
* Deletar um produto na API: `DELETE /products/:id`
* Listar produtos: `GET /products`
* Pesquisar produtos por filtro: `GET /products/search?min_price=10.5&max_price=50&q=superget`
* Busca um produto específico pelo Id: `GET /products/:id`

### Detalhes dos Endpoints

`POST /products`

Este endpoint é utilizado para criar um novo produto.

**Payload body:**

```json
{
 "name": "Product 1",
 "description": "product 1",
 "price": 50.09
}
```

`PUT /products/:id`

Este endpoint é utilizado para atualizar um determinado produto.

**Payload body:**

```json
{
 "name": "Product 1",
 "description": "product 1",
 "price": 66.29
}
```

`DELETE /products/:id`

Este endpoint deleta um produto especificado a partir do id informado.

**Onde:**

`id` - id do produto desejado.

`GET /products`

Este endpoint retorna uma lista de produtos.

`GET /products/search?min_price=10.5&max_price=50&q=superget`

Este endpoint retorna uma lista de produtos. Aceita algums parâmetros:

`min_price` - o preço mínimo

`max_price` - o preço máximo

`q` - valor que pode ser name ou description

`GET /products/:id`

Este endpoint retorna um produto especificado a partir do id informado.

**Onde:**

`id` - id do produto desejado.

### Tecnologias Utilizadass

Este projeto foi desenvolvida com as ferramentas:

* **Java 11**
* **Maven**
* **Swagger**

### Execução

Para executar a API, simplesmente execute:
    
```bash
mvn spring-boot:run
```

Por padrão, a API estará disponível em [http://localhost:9999](http://localhost:9999)

### Documentação

* Swagger (somente ambiente de desenvolvimento): [http://localhost:9999/swagger-ui.html](http://localhost:9999/swagger-ui.html)



