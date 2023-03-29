package tk.jandev.crystalspers.tracker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import tk.jandev.crystalspers.world.Ticker;

import java.util.ArrayList;
import java.util.HashMap;

public class CrystalTracker {
    private final MinecraftClient mc;
    private ArrayList<Double> crystalMap = new ArrayList<>();
    public CrystalTracker(MinecraftClient mc1) {
        this.mc = mc1;
    }


    public void recordAttack(Double time) {
        this.crystalMap.add(time);
    }

    public int getAttacksAfter(Double time) {
        int amount = 0;
        for (Double time1 : this.crystalMap) {
            if (time1 > time) {
                amount++;
            }
        }
        return amount;
    }

    public int getCurrentCPS(Double time) {
        return getAttacksAfter(Ticker.getTime()-20);
    }
}
