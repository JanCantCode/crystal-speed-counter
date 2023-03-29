package tk.jandev.crystalspers.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.jandev.crystalspers.render.CrystalSpeedRenderer;

@Mixin(InGameHud.class)
public abstract class IngameHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {

    }
}
