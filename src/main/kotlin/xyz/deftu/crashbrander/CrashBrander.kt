package xyz.deftu.crashbrander

import net.fabricmc.api.ModInitializer
import net.minecraft.util.crash.CrashReport
import java.io.File

object CrashBrander : ModInitializer {
    private lateinit var config: Config

    override fun onInitialize() {
        config = Config.read(File("config/crashbrander.json"))
    }

    @JvmStatic
    fun addBranding(report: CrashReport) {
        val section = report.addElement("Branding")
        if (!config.name.isNullOrBlank()) section.add("Name", config.name)
        if (!config.version.isNullOrBlank()) section.add("Version", config.version)
        if (!config.author.isNullOrBlank()) section.add("Author", config.author)
        if (!config.website.isNullOrBlank()) section.add("Website", config.website)
    }
}
