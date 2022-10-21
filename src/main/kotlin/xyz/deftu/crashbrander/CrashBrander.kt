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
        val name = config.getModpackName()
        val version = config.getModpackVersion()
        if (!name.isNullOrBlank()) section.add("Name", name)
        if (!version.isNullOrBlank()) section.add("Version", version)
        if (!config.author.isNullOrBlank()) section.add("Author", config.author)
        if (!config.website.isNullOrBlank()) section.add("Website", config.website)
        if (!config.support.isNullOrBlank()) section.add("Support", config.support)
    }
}
