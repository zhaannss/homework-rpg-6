package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;
import java.util.Random;

public class DodgeHandler extends DefenseHandler {
    private final double dodgeChance;
    private final Random random;
    public DodgeHandler(double dodgeChance, long seed) {
        this.dodgeChance = dodgeChance;
        this.random = new Random(seed);
    }
    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        double roll = random.nextDouble();
        if (roll < dodgeChance) {
            System.out.println("[Dodge] " + target.getName() + " evaded the attack! (roll=" + String.format("%.2f", roll) + " < " + dodgeChance + ")");
        } else {
            System.out.println("[Dodge] Attack not evaded (roll=" + String.format("%.2f", roll) + ")");
            passToNext(incomingDamage, target);
        }
    }
}
