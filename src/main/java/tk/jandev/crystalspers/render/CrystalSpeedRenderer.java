package tk.jandev.crystalspers.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import tk.jandev.crystalspers.config.ConfigManager;

public class CrystalSpeedRenderer {

    static MinecraftClient mc = MinecraftClient.getInstance();
    public static void render(DrawContext context, String text, int x, int y, float color) {
        context.drawText(mc.textRenderer, text, (int) x, (int) y, (int) color, true);
    }
}
