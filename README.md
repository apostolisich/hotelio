# Hotelio

This is a demo practice project (many pieces are missing), created using Spring Boot, which consumes and offers simplistic hotel API functionality. It includes an [Embedded Redis server](https://github.com/kstyrc/embedded-redis) for caching as well as an [Embedded H2 Database](https://www.h2database.com/html/main.html) for persistent storage.

## Features
1. Getting a list of hotels for some specified criteria
2. Getting offers for a selected hotel
3. Creating and viewing bookings

## Requests/Responses

**POST /hotel/list**

Request
```
{
    "latitude": 37.983810,
    "longitude": 23.727539,
    "radius": 40
}
```
Response
```
[
    {
        "provider": "amadeus",
        "entries": [
            {
                "id": "ILATH304",
                "iataCode": "ATH",
                "name": "ESPERIDES MAISONETTES - AEGINA",
                "latitude": 37.75412,
                "longitude": 23.4265
            },
            ...
         ]
     }
]
```

---

**POST /hotel/offers**

Request
```
{
    "hotelId": "HSATHAGS",
    "provider": "amadeus",
    "checkInDate": "2022-10-22",
    "checkOutDate": "2022-10-25",
    "adults": 1
}
```
Response
```
{
    "hotelName": "Sea View",
    "offers": [
        {
            "id": "C2IVEVQ0AC",
            "checkInDate": "2022-10-22",
            "checkOutDate": "2022-10-25",
            "adults": 1,
            "children": 0,
            "infants": 0,
            "room": {
                "roomDescription": "TradeFair-Rate\nStandard room A standard room consists of a room with shower-toilet or bathtub-toilet."
            },
            "price": {
                "currency": "EUR",
                "totalPrice": "497.00",
                "taxes": [
                    {
                        "description": "VALUE_ADDED_TAX",
                        "amount": "32.31"
                    },
                    {
                        "description": "TOURISM_TAX",
                        "amount": "3.00"
                    }
                ]
            }
        }
    ]
}
```

---

**POST /hotel/booking**

Request
```
{
	"provider": "amadeus",
	"offerId": "XXXXXXXXX",
	"guests": [
		{
			"title": "Mr",
			"name": "Test",
			"surname": "Test",
			"age": 19
		}
	],
	"contactDetails": {
		"title": "Mr",
		"name": "Test",
		"surname": "Test",
		"address": "test address",
		"phone": "123456789",
		"email": "test@email.com"
	},
	"payment": {
		"cardNumber": "1234567891234567",
		"cvv": "456",
		"expiryDate": "12/27",
		"cardholderName": "TEST TEST"
	}
}
```
Response
```
{
    "bookingReference": "TI2X0K2K03",
    "success": true
}
```

---

**GET hotel/booking/{bookingReference}**

Response
```
{
    "bookingReference": "XXXXXXXXX",
    "creationDate": "18/09/2022 17:23",
    "amount": 497.00,
    "currency": "EUR",
    "hotelName": "SEA VIEW",
    "checkIn": "2022-10-22",
    "checkOut": "2022-10-25",
    "roomDescription": "TradeFair-Rate\nStandard room A standard room consists of a room with shower-toilet or bathtub-toilet.",
    "guests": [
        {
            "title": "Mr",
            "name": "Test",
            "surname": "Test",
            "age": 19
        }
    ],
    "contactDetails": {
        "title": "Mr",
        "name": "Test",
        "surname": "Test",
        "address": "test address",
        "phone": "123456789",
        "email": "test@email.com"
    },
    "payment": {
        "cardNumber": "1234567891234567",
        "cvv": 456,
        "expiryDate": "12/27",
        "cardholderName": "TEST TEST"
    }
}
```
