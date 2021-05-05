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
    "leaderCards": [1, 2, 3, 4] //the IDs of the 4 drafted leader cards
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_START_LEADERS,
    "leaderCards": [2, 3] //the IDs of the 2 selected leader cards
}
```

#### CHOOSE_DEVCARD

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_DEVCARD,
    "structure": {
        "level1": [10, 11, 12, 13],
        "level2": [20, 21, 22, 23],
        "level3": [30, 31, 32, 33]
    }
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_DEVCARD,
    "cardId": //the id of the chosen development card
}
```

#### CHOOSE_PRODUCTION

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_PRODUCTION
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": CHOOSE_PRODUCTION,
    "productions": [1, 3, ...]
}
```

#### LEADER_ACTION

Server --> Client
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTION,
    "leaderCards": [1, 2] //the IDs of possessed leader cards
}
```

Client --> Server
```
messageType: MessageType.GAME_MESSAGE
payload: {
    "gameAction": LEADER_ACTION,
    "cardId": 1 //the ID of the chosen leader card
    "action": "ACTIVATE" //or "DISCARD"
}
```

*Work in progress: more messages to be added soon!*