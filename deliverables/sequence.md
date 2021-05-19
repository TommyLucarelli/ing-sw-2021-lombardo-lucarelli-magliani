# Sequence diagram & messages

## Client-Server messages

Each message has a type and a payload. The server sends a message of a certain type and the client answers with a new message of the same type, with the desired response in the payload. 

The following table describes each type of message and the content of its payload.

| Type | Server | Client |
|------|--------|--------|
|REGISTRATION_MESSAGE|Request of username at start of connection|Username|
|WELCOME_MESSAGE|Welcome message with first instructions|Create/join lobby|
|NUMBER_OF_PLAYERS|Request of the size of the lobby|Lobby size (number of players)
|JOIN_GAME|Request of the lobby id|Lobby id|
||**GAME MESSAGES**||
|CHOOSE_DEVCARD|The development card structure|The development card to buy|
|CHOOSE_PRODUCTION|List of possible production(s)|The production(s) to activate|
|CHOOSE_START_LEADERS|Four leader cards drafted at the start of the game|Selection of two leader cards|
|CHOOSE_START_RESOURCES|Amount of resources to choose at the start of the game|Selected resources and their placement|
|LEADER_ACTIVATION|Check if the player wants to perform a leader card action|True or false|
|LEADER_ACTION|Selection of possible leader cards to play/discard|Selected leader card and the action to perform (activate/discard)|
|MAIN_CHOICE|Request to choose an action between the three main actions|Main action of the turn|
|PICK|Market structure|Selected line/column of the market|
|PLACEMENT_MARKET|Placement of the resources in the warehouse|Resources to place in the warehouse|
|PLACEMENT_DEVCARD|Development card to place in the structure|Positioning of the dev card
|SHORT_UPDATE|Short text message summarizing the progress of the game| ---
|UPDATE|Complete update of the game's components|---

_Note: more messages could be added in the future._

---

## Sequence diagrams

The following diagrams aim to describe each Client-Server interaction.

### - Game creation
![gamecreation.png](images/sequence/gamecreation.png)

### - Join game
![joingame.png](images/sequence/joingame.png)

### - Game preparation
![startgame.png](images/sequence/startgame.png)

### - Start of player turn
![startturn.png](images/sequence/startturn.png)

### - Taking resources from market
![market.png](images/sequence/market.png)

### - Buying a development card
![devcard.png](images/sequence/devcard.png)

### - Activation of production(s)
![production.png](images/sequence/production.png)

### - End of player turn
![endturn.png](images/sequence/endturn.png)

### - End of game
![endgame.png](images/sequence/endgame.png)