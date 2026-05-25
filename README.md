# MMO Server Java

A multiplayer game server built from scratch in Java using TCP sockets and multithreading. Designed as a text-based MMO dungeon crawler where players explore a connected world, fight each other, and progress through a stat and leveling system.

Built without any external game engines or networking frameworks — pure Java backend systems.

---

## Current Features

- Multi-client TCP server with concurrent player handling
- Thread-safe state management using `CopyOnWriteArrayList`
- Connected world of 8 rooms with bidirectional navigation
- Room-based player tracking — players only see and interact with others in the same room
- Turn-based PvP combat with armor degradation mechanic
- Player stat system — HP, Attack, Defense, Level, XP, Gold
- Death and respawn system — dead players must type `RESPAWN` to return
- Room-based chat and global broadcast
- WHO system showing all online players, their location and HP
- Singleton `GameWorld` shared across all threads

---

## Architecture

```
Client (Clients.java)
        ↓  TCP Socket
Server (Server.java)
        ↓  spawns thread per connection
ClientHandler (ClientHandler.java)
        ↓  reads commands, updates state
GameWorld (GameWorld.java)  ←  shared singleton
        ↓  contains
   Room[]  ←→  Room[]  ←→  Room[]
        ↓  players tracked per room
   Player (Player.java)
```

---

## World Map

```
[Cave] ←→ [Dark Forest] ←→ [Mountains]
                ↕
[Tavern] ←→ [Town Square] ←→ [Spawn Room]
                ↕
            [Market]
                ↕
           [Riverbank]
```

---

## Commands

| Command | Description |
|---|---|
| `LOOK` | Describe the current room, exits, and players present |
| `NORTH` / `SOUTH` / `EAST` / `WEST` | Move to an adjacent room |
| `ATTACK <name>` | Attack a player in your current room |
| `SAY <message>` | Send a message to players in your room |
| `WHO` | List all online players, their room and HP |
| `STATS` | Show your current stats |
| `RESPAWN` | Respawn at the starting room after death |

---

## Combat System

- Damage dealt = `max(1, attacker.attack - defender.defense)`
- Each attack degrades the defender's armor by a fixed amount
- Armor never goes below 0
- Death sets HP to 0 — player cannot move or act until they `RESPAWN`
- Respawning returns the player to Spawn Room with 50% HP

---

## Technologies Used

- Java
- TCP Sockets
- Multithreading (`Thread`, `Runnable`)
- Concurrent Collections (`CopyOnWriteArrayList`)
- Object-Oriented Design
- Singleton Pattern

---

## Running The Project

### Start Server
```
javac *.java
java Server
```

### Start Client
Open multiple terminals and run:
```
java Clients
```

---

## Planned Features

- `HELP` command listing all available commands
- XP and leveling system — kill players to gain XP and level up stats
- Room entry and exit broadcasts — world feels alive when players move
- Monster/NPC system with AI behaviour
- Weapons and items with different attack and armor damage values
- Gold drops on kill
- Persistent player progress saved to a database
- Real-time server tick loop for monster movement
- REST API + web client to replace terminal client

---

## Project Goals

This project is being built to explore:

- Multiplayer server architecture
- Concurrent and thread-safe programming
- Real-time networking over TCP
- Systems-level backend engineering
- Game state management across multiple threads
- Scalable MMO server design patterns

---

## Learning Focus

This project prioritizes backend systems design, networking, and multiplayer architecture. Every feature is built from scratch to understand how real game servers work under the hood.