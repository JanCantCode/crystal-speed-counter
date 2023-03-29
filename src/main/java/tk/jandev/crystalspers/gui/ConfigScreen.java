package tk.jandev.crystalspers.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import tk.jandev.crystalspers.config.ConfigManager;

import java.io.IOException;

public class ConfigScreen extends Screen {
    final MinecraftClient mc = MinecraftClient.getInstance();
    public ConfigScreen(Text title) {
        super(title);
    }

    protected void init() {
        ButtonWidget posWidget = new ButtonWidget(this.width / 2-25, this.height / 2, 50, 20, Text.of("position"), button -> {
            mc.setScreen(new PositionConfigScreen(Text.of("position")));
        });
        this.addDrawableChild(posWidget);

        ButtonWidget colorWidget = new ButtonWidget(this.width / 2-15, (int) (this.height / 2-30), 35, 20, Text.of("color"), button -> {
            mc.setScreen(new ColorConfigScreen(Text.of("color")));
        });
        this.addDrawableChild(colorWidget);

        ButtonWidget closeButton = new ButtonWidget(this.width / 2- 15, (int) (this.height / 1.1), 30, 20, Text.of("exit"), button -> {
            mc.setScreen(null);
            try {
                ConfigManager.safe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.addDrawableChild(closeButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        mc.textRenderer.draw(matrices, "Crystal Per Second settings", (float) (this.width/2), (float) 20, 0x43ff64ff);
        this.renderBackgroundTexture(0);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
