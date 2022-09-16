package xyz.deftu.crashbrander

import net.fabricmc.api.ModInitializer
import net.minecraft.util.crash.CrashReport
import xyz.deftu.crashbrander.config.Config

object CrashBrander : ModInitializer {
    override fun onInitialize() {
    }

    @JvmStatic
    fun addBranding(report: CrashReport) {
        val config = Config.INSTANCE
        if (!config.enabled || !config.printTypes.contains(Config.PrintType.CRASH))
            return

        val section = report.addElement("Pack Branding")
        if (!config.name.isNullOrBlank()) section.add("Name", config.name)
        if (!config.version.isNullOrBlank()) section.add("Version", config.version)
        if (!config.author.isNullOrBlank()) section.add("Author", config.author)
        if (!config.website.isNullOrBlank()) section.add("Website", config.website)
        if (!config.support.isNullOrBlank()) section.add("Support", config.support)
    }
}
