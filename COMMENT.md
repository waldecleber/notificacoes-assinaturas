- Aplicação disponivel na porta 9003; (OK)

- Testando o envio de uma subscription usando os parametros a seguir: (OK)

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


- Persistindo os dados em MySQL (OK)

- Realizando auditoria na tabela EventHistory através de EntityListener usando: (OK)

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
