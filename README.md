**1. Получить список всех регионов:**

Запрос: GET http://localhost:8082/address_service/regions

Пример ответа:
```
[
   {
      "objectId": 11,
      "name": "Адыгея",
      "type": "Респ",
      "nameType": "Адыгея Респ"
   }
]
```


**2. Получить список адресов по региону и части адреса:**

Запрос: POST http://localhost:8082/address_service/suggestions (передается json с параметрами: regionObjectId - Id региона, namePart - часть адреса для поиска, тело запроса передавать в UTF-8)

Пример запроса:
```
{
  "regionObjectId": "807356",
  "namePart": "пуш"
}
```
Пример ответа:
```
[
   {
      "objectId": 813133,
      "guid": "b56ae9c6-3404-4e23-bf1d-d27d9c550855",
      "name": "Пушечная",
      "type": "ул",
      "fullName": "Московская обл, Домодедово г, Западный мкр., Пушечная ул"
   }
]
```
