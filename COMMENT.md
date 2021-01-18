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
- [ ] Enfileirar notificaçoes para ser consumidas 
- [ ] Persistir dados após enviar para fila
- [ ] Criar Docker para conteinerizar aplicação
- [ ] Automatizar processo de envio de notificacoes
