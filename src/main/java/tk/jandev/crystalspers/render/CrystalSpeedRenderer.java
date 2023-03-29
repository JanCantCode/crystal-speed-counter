package tk.jandev.crystalspers.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import tk.jandev.crystalspers.config.ConfigManager;

public class CrystalSpeedRenderer {

    static MinecraftClient mc = MinecraftClient.getInstance();
    public static void render(MatrixStack matrices, String text, int x, int y, float color) {
        if (matrices.isEmpty() || matrices==null)return;
        mc.textRenderer.draw(matrices, text, (float) x, (float) y, (int) color);
    }


}
