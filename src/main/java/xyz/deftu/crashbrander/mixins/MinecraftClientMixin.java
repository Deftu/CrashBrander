package xyz.deftu.crashbrander.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.deftu.crashbrander.CrashBrander;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin {
    @Inject(method = "addDetailsToCrashReport", at = @At("TAIL"))
    private void onDetailsAddedToCrashReport(CrashReport crashReport, CallbackInfoReturnable<CrashReport> cir) {
        CrashBrander.addBranding(crashReport);
    }
}
