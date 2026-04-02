package com.narxoz.rpg.command;
import com.narxoz.rpg.arena.ArenaFighter;

public class DefendCommand implements ActionCommand {
    private final ArenaFighter target;
    private final double dodgeBoost;
    public DefendCommand(ArenaFighter target, double dodgeBoost) {
        this.target = target;
        this.dodgeBoost = dodgeBoost;
    }
    @Override
    public void execute() {
        target.modifyDodgeChance(dodgeBoost);
        System.out.println("[Defend] " + target.getName() + " dodge chance boosted by +" + (int)(dodgeBoost * 100) + "%. Current: " + String.format("%.0f", target.getDodgeChance() * 100) + "%");
    }
    @Override
    public void undo() {
        target.modifyDodgeChance(-dodgeBoost);
        System.out.println("[Undo Defend] Removed dodge boost from " + target.getName());
    }
    @Override
    public String getDescription() {
        return "Defend (dodge boost: +" + (int)(dodgeBoost * 100) + "%)";
    }
}
