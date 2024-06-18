**1. Получить список всех регионов:**

Запрос: GET http://localhost:8082/address_service/regions

Пример ответа:
```
[
   {
      "objectId": 1405113,
      "name": "Москва",
      "type": "г",
      "nameType": "Москва г"
   }
]
```


**2. Получить список адресов по региону и части адреса:**

Запрос: POST http://localhost:8082/address_service/suggestions (передается json с параметрами: regionObjectId - Id региона, namePart - часть адреса для поиска, тело запроса передавать в UTF-8)

Пример запроса:
```
{
  "regionObjectId": "1405113",
  "namePart": "пушк"
}
```
Пример ответа:
```
[
  {
    "objectId": 1409452,
    "guid": "b36b0d6a-6c8a-46db-ba85-b5a8c383de97",
    "name": "Пушкина",
    "type": "ул",
    "fullName": "Москва г, Пушкина ул"
  }
]
```
