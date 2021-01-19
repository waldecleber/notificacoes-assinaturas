# Assinatura Globoplay

API criada para o desafio de envio de notificaçoes de assinaturas Globoplay

## Tecnologias

O projeto foi desenvolvido utilizando as seguintes tecnologias:

* Java - versão 8
* TDD com mockito
* Spring boot - 2.1.6
* Swagger  
* MySQL 
* RabbitMQ
* Flyway para versionamento do banco
* Gradle
* Docker
* Git para versionamentoda aplicação

### Docker
O Docker compose possui as seguintes imagens:
- RabbitMQ
- MySQL
- App Spring Boot

Aplicação disponível em:
```
http://localhost:9003/swagger-ui.html
```

Adminer for MySQL disponivel em:
```
http://localhost:8083/?server=mysqldb
user:root
password: admin
```

RabbitMQ disponível em:
```
http://localhost:15672/
user:guest
password: guest
```


## Guia de Instalação

Baixar o projeto no repositorio gitHub disponível em [https://github.com/jeandropercevalnt/globo-waldecleber-oliveira.git]

### Passos para executar a aplicação
Execute os seguintes comandos:
```sh
cd globo-waldecleber-oliveira
docker-compose up -d --build
```



Após subir o container, a API estará disponível no seguinte endereço:
```
http://localhost:9003/swagger-ui.html
```



### Development
```sh
$ ./gradlew clean build
```
```sh
$ ./gradlew bootRun
```

```







- [x] Aplicação disponivel na porta 9003; (OK)

- [x] Testando o envio de uma subscription usando os parametros a seguir: (OK)

```
curl --location --request POST 'http://localhost:9003/subscriptions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "5793cf6b3fd833521db8c420955e6f08",
    "status_id": {
        "name": "SUBSCRIPTION_PURCHASED"
    }
}'
```


- [x] Persistindo os dados em MySQL (OK)

- [x] Realizando auditoria na tabela EventHistory através de EntityListener usando: (OK)

```
    @PrePersist
    public void onPrePersist(Subscription subscription) {
        audit("INSERT", subscription);
        LOGGER.info(subscription.getId());
    }

    @PreUpdate
    public void onPreUpdate(Subscription subscription) {
        audit("UPDATE", subscription);
        LOGGER.info(subscription.getId());
    }
```
- [x] Enfileirar notificaçoes para ser consumidas 
    
- [x] Persistir dados após enviar para fila
    - Enviar dados para a fila do RabbitMQ 
    - Foi criado Auditoria através do uso de AuditListener
    
- [x] Criar Docker para conteinerizar aplicação
    - Usando docker-compose
- [x] Automatizar processo de envio de notificacoes
    - Shell Script sendNotifications
