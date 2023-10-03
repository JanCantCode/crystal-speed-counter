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
    private static boolean enabled = true;
    @Override
    public void onInitializeClient() {
        MinecraftClient mc = MinecraftClient.getInstance();

        CrystalTracker tracker = new CrystalTracker(mc);


        try {
            ConfigManager.load();
            ConfigManager.safe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (((entity instanceof EndCrystalEntity) || (entity instanceof MagmaCubeEntity) || (entity instanceof SlimeEntity)) && world.isClient) {
                tracker.recordAttack(Ticker.getTime());
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player==null)return; // is this ugly? yeah. did I ask? nahhh
            Ticker.tick();
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("counterconfig").executes(context -> {
            mc.send(() -> mc.setScreen(new ConfigScreen(Text.of("config"))));
            return 1;
        })));

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (!enabled) return;
            //String.valueOf(tracker.getAttacksAfter(ticker.getTime()-20))
            drawContext.drawText(mc.textRenderer, String.valueOf(tracker.getAttacksAfter(Ticker.getTime()-20)), (int) ConfigManager.getX()+28, (int) ConfigManager.getY(), (int) ConfigManager.getCounterColor(), true);
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("togglecounter").executes(context -> {
            enabled = !enabled;
            context.getSource().sendFeedback(Text.of("§7Toggled Crystal Speed Counter "+(enabled ? "§aon" : "§coff")));
            return 1;
        })));
    }
}
