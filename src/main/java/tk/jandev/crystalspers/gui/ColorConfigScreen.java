package tk.jandev.crystalspers.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import tk.jandev.crystalspers.config.ConfigManager;

import java.io.IOException;
import java.util.Objects;

public class ColorConfigScreen extends Screen {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    public ColorConfigScreen(Text title) {
        super(title);
    }

    protected void init() {


        TextFieldWidget colorWidget = new TextFieldWidget(mc.textRenderer, this.width / 2, this.height / 2, 70, 20, Text.of("test"));
        this.addDrawableChild(colorWidget);

        ButtonWidget done = new ButtonWidget(this.width/2, (int) (this.height/1.6), 40, 20, Text.of("Done"), new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                String content = colorWidget.getText();
                int hex = 0;
                try {
                    hex = Integer.decode(content);
                } catch (NumberFormatException e) {
                    mc.setScreen(null);// close the screen incase the input is not a valid integer
                    mc.player.sendMessage(Text.of("The color you put in was not valid!"));
                    return; // we don't want to update the color to 0 if the color is invalid
                }
                ConfigManager.setCounterColor(hex);
                mc.player.sendMessage(Text.of("Updated color"));
                mc.setScreen(null);
                try {
                    ConfigManager.safe();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;


            }
        });

        this.addDrawableChild(done);

        ButtonWidget closeButton = new ButtonWidget(this.width / 2- 15, (int) (this.height / 1.1), 30, 20, Text.of("exit"), button -> mc.setScreen(null));

        this.addDrawableChild(closeButton);
    }


    public void tick() {
    }

    public int convertHex(String hex) {
        String noHashTag = hex.replace("#", "0x");

        try {
            return Integer.parseInt(noHashTag);
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        this.textRenderer.draw(matrices, "color", (float) (this.width/2.35), (float) (this.height/1.88), 259324);
        super.render(matrices, mouseX, mouseY, delta);
    }


}
