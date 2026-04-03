# Homework 6 — Grand Arena Tournament
## Chain of Responsibility + Command Pattern

---

## What Was Implemented

### Chain of Responsibility (Defense Chain)
Incoming damage passes through 4 handlers in order:

- **DodgeHandler** — random roll, if successful the attack is fully absorbed and the chain stops
- **BlockHandler** — reduces damage by a percentage, always passes the remainder forward
- **ArmorHandler** — reduces damage by a flat value, always passes the remainder forward
- **HpHandler** — terminal handler, applies the final damage to HP

### Command Pattern (Action Queue)
Hero actions are encapsulated as command objects:

- **AttackCommand** — deals damage to the opponent, supports undo
- **HealCommand** — heals the hero using a potion, supports undo
- **DefendCommand** — boosts dodge chance, supports undo
- **ActionQueue** — manages the queue: enqueue, undoLast, executeAll

### TournamentEngine
Combines both patterns in a multi-round battle. The hero queues actions each round, and opponent attacks are routed through the defense chain.

---

## How to Run

**Compile (PowerShell):**
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
```

**Run:**
```powershell
java -cp out com.narxoz.rpg.Main
```

---

## Project Structure

```
src/com/narxoz/rpg/
├── Main.java
├── arena/
│   ├── ArenaFighter.java
│   ├── ArenaOpponent.java
│   └── TournamentResult.java
├── chain/
│   ├── DefenseHandler.java
│   ├── DodgeHandler.java
│   ├── BlockHandler.java
│   ├── ArmorHandler.java
│   └── HpHandler.java
├── command/
│   ├── ActionCommand.java
│   ├── AttackCommand.java
│   ├── HealCommand.java
│   ├── DefendCommand.java
│   └── ActionQueue.java
└── tournament/
    └── TournamentEngine.java
```