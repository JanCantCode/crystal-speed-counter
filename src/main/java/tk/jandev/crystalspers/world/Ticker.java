package tk.jandev.crystalspers.world;

import net.minecraft.world.tick.Tick;

public class Ticker {
    static double time = 0;


    public static void tick() {
        time++;
    }

    public static double getTime() {
        return time;
    }
}
