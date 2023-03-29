package tk.jandev.crystalspers.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.transformer.Config;
import tk.jandev.crystalspers.config.ConfigManager;
import tk.jandev.crystalspers.gui.ColorConfigScreen;
import tk.jandev.crystalspers.gui.ConfigScreen;
import tk.jandev.crystalspers.gui.PositionConfigScreen;
import tk.jandev.crystalspers.render.CrystalSpeedRenderer;
import tk.jandev.crystalspers.tracker.CrystalTracker;
import tk.jandev.crystalspers.world.Ticker;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class CrystalspersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MinecraftClient mc = MinecraftClient.getInstance();

        CrystalTracker tracker = new CrystalTracker(mc);
        Ticker ticker = new Ticker();


        try {
            ConfigManager.load();
            ConfigManager.safe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (((entity instanceof EndCrystalEntity) || (entity instanceof MagmaCubeEntity) || (entity instanceof SlimeEntity)) && world.isClient) {
                tracker.recordAttack(ticker.getTime());
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ticker.tick();
            if (mc.player==null)return; // is this ugly? yeah. did I ask? nahhh
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("config").executes(context -> {
            mc.send(() -> mc.setScreen(new ConfigScreen(Text.of("config"))));
            return 1;
        })));

        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {

            //String.valueOf(tracker.getAttacksAfter(ticker.getTime()-20))
            mc.textRenderer.draw(matrices, String.valueOf(tracker.getAttacksAfter(ticker.getTime()-20)), ConfigManager.getX()+28, ConfigManager.getY(), ConfigManager.getCounterColor());
        });


    }
}
