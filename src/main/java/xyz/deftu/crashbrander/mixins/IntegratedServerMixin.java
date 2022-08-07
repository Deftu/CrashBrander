package xyz.deftu.crashbrander.mixins;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.crashbrander.CrashBrander;

@Mixin({IntegratedServer.class})
public class IntegratedServerMixin {
    @Inject(method = "setCrashReport", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setCrashReportSupplier(Lnet/minecraft/util/crash/CrashReport;)V", shift = At.Shift.BEFORE))
    private void onCrashReportSet(CrashReport crashReport, CallbackInfo ci) {
        CrashBrander.addBranding(crashReport);
    }
}
