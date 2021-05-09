# Messages

This document describes the structure of  each message sent to the Client and its response.

The Client-Server communication is made possible by two type of objects: **RequestMsg** and **ResponseMsg**, 
both extensions of **NetworkMsg**. The server sends a RequestMsg to the client, who processes the payload and then
proceeds to send the adequate ResponseMsg. In terms of structure there aren't big differences between the two objects: 
they both have a MessageType field and a JsonObject payload. The ResponseMsg also has a field containing the UUID of the
related RequestMsg.

### The MessageType field
The messages are correctly identified and processed through the MessageType field, which contains the type of the 
message. Each message type has different payloads whether it is sent from the Server or from the Client. The game 
related messages have the `GAME_MESSAGE` type: the action they refer to is signaled by a `gameAction` field in the
payload of the message.

## Message types and their payload

*Note: in this section figure only some messages. The messages that only require a string message and a basic 
input from the user are not described.*

#### CHOOSE_START_LEADERS

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_START_LEADERS,
    "playerOrder": int //the order of the player in the turn
    "leaderCards": int[4] //the IDs of the 4 drafted leader cards
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_START_LEADERS,
    "discardedLeaders": int[2] //the IDs of the 2 leader cards discarded by the player
    "playerID": int //player id
}
```

#### CHOOSE_START_RESOURCES

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_START_RESOURCES,
    "resources": int //the number of resources the player can take
    "faithPoints": int //the number of faith points for the player
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_START_RESOURCES,
    "placed": ArrayList<Resource> //the array with the placement of the acquired resources into the warehouse
    "playerID": int //player id
}
```

#### LEADER_ACTIVATION

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTIVATION,
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTIVATION,
    "activation": boolean //representing the choice of activate/discard a leader card or not
}
```

#### LEADER_ACTION

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTION,
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTION,
    "cardId": int //the ID of the chosen leader card
    "action": boolean // actvivate or discard
}
```
#### MAIN_CHOICE

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": MAIN_CHOICE,
    "leaderCard": int //the id of the leader card activated or discarded in that very turn 
    "action": boolean // actvivate or discard
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": MAIN_CHOICE,
    "actionChoice": "market" | "production" | "buyDevCard" //action chosen by the user
}
```

#### CHOOSE_DEVCARD

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_DEVCARD,
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_DEVCARD,
    "line": int //line of devCard structure
    "column": int //column of devCard structure
}
```

#### DEVCARD_PLACEMENT

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": DEVCARD_PLACEMENT,
    "freeSpots": ArrayList<Integer> //representing the free spots where the player can put the devCard
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": DEVCARD_PLACEMENT,
    "index": int //chosen spot for the devCard
}
```

#### CHOOSE_PRODUCTION

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_PRODUCTION,
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_PRODUCTION,
    "productions": ArrayList<Integer> //with the selected productions (included basics and specials)
}
```

#### PICK

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": PICK,
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": PICK,
    "choice": "line" | "column" //relative to the market
    "number": int //for the line/column number
    "reource": Resource //representing the special resource to swop with the white one
}
```

#### WAREHOUSE_PLACEMENT

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": WAREHOUSE_PLACEMENT,
    "resourcesArray": ArrayList<Resource> //representing the resources that the player can dispose in the warehouse
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": WAREHOUSE_PLACEMENT,
    "placed": ArrayList<Resource> //the array with the placement of the acquired resources into the warehouse
}
```

#### UPDATE

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": UPDATE,
    "currentPlayerID": int, //player who has just finished the turn
    "nextPlayerID": int, //player who is going to play in the next turn
    "message": "Giocatore 1 ha attivato la produzione", //basic message for info
    "market":{
        "structure": int[12], //array representing arrangement of marbles in the market
        "reserveMarble": int 
    },
    "devCardStructure": int[3][4], //matrix representing the top layer of the devCard structure just with the id of the cards
    "player": {
        "faithTrack":{
            "points": 12,
            "favourCards": boolean[3], //favourCards activation
        },

        "devCardSlots":{
            "structure": int[3][3],
        },
        "warehouse":{
            "structure": ArrayList<Resource>,
        },
        "strongbox":{
            "structure": int[4],
        },
        "activatedLeaderCards":int[2],
        "discardedLeaderCards":int[2],
}
    
}
```

#### INITIAL_UPDATE

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": INITIAL_UPDATE,
    "market":{
        "structure": int[12], //array representing arrangement of marbles in the market
        "reserveMarble": int 
    },
    "devCardStructure": int[3][4], //matrix representing the top layer of the devCard structure just with the id of the cards
    "numOfPlayers": int //number of players
    "playerID1": {
        "playerID": int,
        "playerName": String,
        "faithTrack":{
            "points": 12,
            "favourCards": boolean[3], //favourCards activation
        },
        "warehouse":{
            "structure": ArrayList<Resource>,
        },  
    }
    "playerID2": {
        "playerID": int,
        "playerName": String
        "faithTrack":{
            "points": 12,
            "favourCards": boolean[3], //favourCards activation
        },
        "warehouse":{
            "structure": ArrayList<Resource>,
        },  
    }
     "playerID3": {
         "playerID": int,
        "playerName": String
        "faithTrack":{
            "points": 12,
            "favourCards": boolean[3], //favourCards activation
        },
        "warehouse":{
            "structure": ArrayList<Resource>,
        },  
    }
    
}
```






*Work in progress: more messages to be added soon!*
