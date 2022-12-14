package xyz.deftu.crashbrander.mixins;

import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.deftu.crashbrander.CrashBrander;

import java.io.File;
import java.util.List;

/**
 * Adapted from Bolt under MIT
 * <a href="https://github.com/Jab125/Bolt/blob/bad3f50050e80d1b6220600582f3710be541a97a/LICENSE">LICENSE</a>
 */
@Mixin({CrashReport.class})
public class CrashReportMixin {
    @Shadow @Final private List<CrashReportSection> otherSections;

    @Inject(method = "writeToFile", at = @At("HEAD"))
    private void crashbrander$addSection(File file, CallbackInfoReturnable<Boolean> cir) {
        CrashReportSection section = new CrashReportSection("Modpack Branding");
        CrashBrander.addBranding(section);
        otherSections.add(section);
    }
}
