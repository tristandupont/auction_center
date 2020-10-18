# Auction Center

A web application that manages an auction house.

## Requirements

- Java 11+
- Maven

## Application server

Main class:

```java
com.github.tristandupont.auction_center.AuctionCenterServer
```

Server starts on port `8080`.

## Usage

### Auction house

#### Create auction house

Syntax:

`POST http://localhost:8080/auctionHouses`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X POST \
-d '{"name": "Drouot"}' \
"http://localhost:8080/auctionHouses"
```

Response:

```json
{"auctionHouseId":"dbb3807e-a867-40e3-a132-853c8f56303f"}
```

#### List all auction houses

Syntax:

`GET http://localhost:8080/auctionHouses`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X GET \
"http://localhost:8080/auctionHouses"
```

Response:

```json
[
  {
    "id": "15270193-b34f-4371-8a6b-0097526f2be6",
    "name": "Drouot"
  },
  {
    "id": "9f1d2ae7-d701-4454-9a62-3d3ad4bcd625",
    "name": "Christie's"
  }
]
```

#### Delete a specific auction house

Syntax:

`DELETE http://localhost:8080/auctionHouses/{auctionHouseId}`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X DELETE \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625"
```

### Auction

#### Create auction

Syntax:

`POST http://localhost:8080/auctionHouses/{auctionHouseId}/auctions`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X POST \
-d '{"name": "Green bicycle", "description": "A wonderful bicycle !", "startTime": "2020-10-15T12:00:00Z", "endTime": "2021-12-18T12:00:00Z", "startPrice": 30}' \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions"
```

Response:

```json
{"auctionId":"53e6da5d-005b-49fc-86d0-f496daaa9e49"}
```

#### List all auctions for a given auction house

Syntax:

`GET http://localhost:8080/auctionHouses/{auctionHouseId}/auctions?status={auctionStatus}`

Request param `status` is optional and allowed values are: 
`NOT_DELETED`, `NOT_STARTED`, `RUNNING`, `FINISHED`, `DELETED`.

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X GET \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions"
```

Response:

```json
[
  {
    "id": "53e6da5d-005b-49fc-86d0-f496daaa9e49",
    "name": "Green bicycle",
    "description": "A wonderful bicycle !",
    "startTime": "2020-10-17T12:00:00",
    "endTime": "2021-10-18T12:00:00",
    "startPrice": 30
  },
  {
    "id": "62ee601a-e06d-475c-8dcf-e5610a53ac4e",
    "name": "Red car",
    "description": "A small red car",
    "startTime": "2020-10-17T12:00:00",
    "endTime": "2020-10-18T13:00:00",
    "startPrice": 1000
  },
  {
    "id": "1dd14a6b-1e04-4ffd-9e87-615686a64cfd",
    "name": "Blue car",
    "description": "A big blue car",
    "startTime": "2020-10-17T12:00:00",
    "endTime": "2020-10-18T13:00:00",
    "startPrice": 10000
  }
]
```

#### Delete a specific auction

Syntax:

`DELETE http://localhost:8080/auctionHouses/{auctionHouseId}/auctions/{auctionId}`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X DELETE \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions/62ee601a-e06d-475c-8dcf-e5610a53ac4e"
```

### Bidding

#### Create a bidding for an auction

Syntax:

`POST http://localhost:8080/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X POST \
-d '{"userName": "John", "amount": 40}' \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions/ea397ecc-634e-4379-8aeb-5c8bee75025e/biddings"
```

#### List all bidding

Syntax:

`GET http://localhost:8080/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X GET \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions/8e059bdd-dc5c-46be-ada0-9d939c9f1cb1/biddings"
```

Response:

```json
[
  {
    "userName": "Tom",
    "amount": 1500
  },
  {
    "userName": "Julien",
    "amount": 1700
  },
  {
    "userName": "Mary",
    "amount": 1800
  }
]
```

#### Show the winner of an auction

Syntax:

`GET http://localhost:8080/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings/winner`

Sample:

```shell script
curl \
-H "Content-Type: application/json" \
-X GET \
"http://localhost:8080/auctionHouses/9f1d2ae7-d701-4454-9a62-3d3ad4bcd625/auctions/8e059bdd-dc5c-46be-ada0-9d939c9f1cb1/biddings/winner"
```

Response:

```json
{
  "hasWinner": true,
  "userName": "Mary",
  "amount": 1800
}
```

## Improvements

- Test are given as example, more test are needed (it takes a lot of time).

## Known limitations

- Dates must be in UTC timezone
- Concurrency is not handled
- There are no authentication
- Amount of biddings are abstract (no currency)
- Some parameters validation are not implemented
