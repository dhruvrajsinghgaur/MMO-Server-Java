# Java Multiplayer Server

A multiplayer server framework built in Java using sockets and multithreading, designed to evolve into a text-based MMO dungeon crawler.

The project focuses on learning real multiplayer backend architecture and concurrent systems programming from scratch without relying on external game engines or networking frameworks.

---

# Current Features

- Multi-client TCP server
- Concurrent player handling using threads
- Real-time server-client communication
- Broadcast messaging system
- Shared synchronized server state
- Atomic shared counter commands (`INC` / `DEC`)
- Thread-safe client storage using `CopyOnWriteArrayList`

---

# Planned Features

- Shared room/world system
- Player movement commands
- Room-based chat
- Global shout system
- Turn-based combat
- Monster/NPC system
- Player stats and leveling
- Gold and inventory system
- Persistence and save system
- Real-time server tick loop
- Modular command parser

---

# Technologies Used

- Java
- TCP Sockets
- Multithreading
- Concurrent Collections
- AtomicInteger
- Object-Oriented Programming

---

# Architecture

```text
Client
   ↓
ClientHandler Thread
   ↓
Command Processing
   ↓
Shared Server State
```

The server acts as the authoritative source of truth for all connected players and world interactions.

---

# Current Commands

```text
INC
DEC
```

More MMO-style commands and world mechanics will be added as the project evolves.

---

# Project Goals

This project is being built to explore:

- Multiplayer server architecture
- Concurrent programming
- Real-time networking concepts
- Systems-level backend engineering
- Scalable game-server design

---

# Running The Project

## Start Server

```bash
javac *.java
java Server
```

## Start Client

Open multiple terminals and run:

```bash
java Clients
```

---

# Future Direction

The long-term goal is to evolve this project into a fully connected multiplayer dungeon crawler featuring:

- shared rooms
- player interaction
- monsters and combat
- persistent progression
- scalable multiplayer systems
- real-time world events

---

# Learning Focus

This project prioritizes backend systems design, networking, and multiplayer architecture over graphics or frontend development.