package xyz.deftu.crashbrander

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.slf4j.LoggerFactory
import xyz.deftu.crashbrander.config.Config

object CrashBranderPreLaunch : PreLaunchEntrypoint {
    private val logger = LoggerFactory.getLogger("Crash Brander")

    override fun onPreLaunch() {
        val config = Config.INSTANCE
        if (!config.enabled || !config.printTypes.contains(Config.PrintType.LOGS))
            return

        logger.info("CrashBrander log printing:\n${buildString {
            append("-- Pack Branding --").append("\n")
            append("Details:").append("\n")
            appendInfo(config.name != null, "Name", config.name)
            appendInfo(config.version != null, "Version", config.version)
            appendInfo(config.author != null, "Author", config.author)
            appendInfo(config.website != null, "Website", config.website)
            appendInfo(config.support != null, "Support", config.support)
        }}")
    }

    private fun StringBuilder.appendInfo(
        option: Boolean?,
        key: String,
        value: String?
    ) {
        if (option == false)
            return

        append("\t").append("$key: ").append(value).append("\n")
    }
}
