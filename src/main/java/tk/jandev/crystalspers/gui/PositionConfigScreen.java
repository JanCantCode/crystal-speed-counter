package tk.jandev.crystalspers.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import tk.jandev.crystalspers.config.ConfigManager;

import java.awt.*;
import java.io.IOException;

public class PositionConfigScreen extends Screen {
    MinecraftClient mc = MinecraftClient.getInstance();
    int currentX = ConfigManager.getX();
    int currentY = ConfigManager.getY();
    double buttonPosX = 0;
    double buttonPosY = 0;
    public PositionConfigScreen(Text title) {
        super(title);
    }

    public void init() {

        /*ButtonWidget closeButton = new ButtonWidget((int) (this.width / 2.1), (int) (this.height / 1.1), 40, 20, Text.of("done"), button -> {
            try {
                ConfigManager.safe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mc.setScreen(null);
        });*/
        ButtonWidget closeButton = ButtonWidget.builder(Text.of("Done"), button -> {
            try {
                ConfigManager.safe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mc.setScreen(new ConfigScreen(Text.of("config")));
        }).dimensions(this.width / 2 - 63, (int) (this.height / 1.1), 125, 20).build();
        
        buttonPosX = this.width/2.1;
        buttonPosY = this.height/1.1;
        this.addDrawableChild(closeButton);

    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawText(this.textRenderer, "5 crystals/s", mouseX-30, mouseY, ConfigManager.getCounterColor(), true);
        context.drawText(this.textRenderer, "5 crystals/s", ConfigManager.getX(), ConfigManager.getY(), ConfigManager.getCounterColor()+100000, true);


        context.drawHorizontalLine(0, this.width, mouseY, Color.GREEN.getRGB());
        context.drawVerticalLine(mouseX, 0, this.height, Color.GREEN.getRGB());


        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {


        if (!super.mouseClicked(mouseX, mouseY, button)) {
            ConfigManager.setX((int) mouseX-30);
            ConfigManager.setY((int) mouseY);
            System.out.println("updated to " + ConfigManager.getX() + " shall be "+(mouseX-30));

            try {
                ConfigManager.safe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static boolean collision(int x, int y, int width, int height, double mouseX, double mouseY) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }
}
