package com.narxoz.rpg;
import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import com.narxoz.rpg.tournament.TournamentEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 6 Demo: Chain of Responsibility + Command ===\n");
        // Герой: 100 HP, 20% уклонение, 25 блок, 5 броня, 18 атака, 3 зелья
        ArenaFighter hero = new ArenaFighter("Aisha", 100, 0.20, 25, 5, 18, 3);
        // Соперник: 90 HP, 14 атака
        ArenaOpponent opponent = new ArenaOpponent("Champion", 90, 14);
        // -----------------------------------------------------------------------
        // Part 1 — Command Queue Demo
        // -----------------------------------------------------------------------
        System.out.println("--- Command Queue Demo ---");

        ActionQueue queue = new ActionQueue();
        // Ставим в очередь три действия
        queue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
        queue.enqueue(new HealCommand(hero, 20));
        queue.enqueue(new DefendCommand(hero, 0.15));
        // Печатаем все команды в очереди
        System.out.println("Queued actions (" + queue.getCommandDescriptions().size() + "):");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  - " + desc);
        }
        // Отменяем последнюю команду (DefendCommand)
        System.out.println("\nUndoing last queued action...");
        queue.undoLast();
        System.out.println("Queue after undo (" + queue.getCommandDescriptions().size() + "):");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  - " + desc);
        }
        // Снова добавляем DefendCommand и выполняем всё
        queue.enqueue(new DefendCommand(hero, 0.15));
        System.out.println("\nExecuting all queued commands...");
        queue.executeAll();

        System.out.println("Queue size after executeAll: " + queue.getCommandDescriptions().size() + " (should be 0)");
        // -----------------------------------------------------------------------
        // Part 2 — Defense Chain Demo
        // -----------------------------------------------------------------------
        System.out.println("\n--- Defense Chain Demo ---");
        // Строим цепочку вручную: Dodge -> Block -> Armor -> HP
        DefenseHandler dodge = new DodgeHandler(0.50, 99L);
        DefenseHandler block = new BlockHandler(0.30);
        DefenseHandler armor = new ArmorHandler(5);
        DefenseHandler hp    = new HpHandler();
        dodge.setNext(block).setNext(armor).setNext(hp);

        System.out.println("Chain: DodgeHandler(50%) -> BlockHandler(30%) -> ArmorHandler(5) -> HpHandler");
        System.out.println("Sending 20 incoming damage through the defense chain...");
        System.out.println("Hero HP before: " + hero.getHealth());
        dodge.handle(20, hero);
        System.out.println("Hero HP after:  " + hero.getHealth());
        // -----------------------------------------------------------------------
        // Part 3 — Full Tournament Demo
        // -----------------------------------------------------------------------
        System.out.println("\n--- Full Arena Tournament ---");
        // Erlan: 120 HP, 25% уклонение, 25 блок, 8 броня, 22 атака, 3 зелья
        ArenaFighter tournamentHero     = new ArenaFighter("Erlan",    120, 0.25, 25, 8, 22, 3);
        ArenaOpponent tournamentOpponent = new ArenaOpponent("Iron Vane", 100, 16);
        System.out.println("Hero:     " + tournamentHero.getName()
                + " | HP=" + tournamentHero.getHealth()
                + " | ATK=" + tournamentHero.getAttackPower()
                + " | Dodge=" + (int)(tournamentHero.getDodgeChance()*100) + "%");
        System.out.println("Opponent: " + tournamentOpponent.getName()
                + " | HP=" + tournamentOpponent.getHealth()
                + " | ATK=" + tournamentOpponent.getAttackPower());
        System.out.println();

        TournamentResult result = new TournamentEngine(tournamentHero, tournamentOpponent)
                .setRandomSeed(42L)
                .runTournament();
        System.out.println("\nWinner : " + result.getWinner());
        System.out.println("Rounds : " + result.getRounds());
        System.out.println("Battle log:");
        for (String line : result.getLog()) {
            System.out.println("  " + line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}