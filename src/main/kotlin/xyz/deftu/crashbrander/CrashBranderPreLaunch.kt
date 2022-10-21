package xyz.deftu.crashbrander

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import xyz.deftu.crashbrander.config.Config

object CrashBranderPreLaunch : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        val config = Config.INSTANCE
        if (!config.enabled || !config.printTypes.contains(Config.PrintType.LOGS))
            return

        val name = config.getModpackName()
        val version = config.getModpackVersion()
        CrashBranderConstants.logger.info("CrashBrander log printing:\n${buildString {
            append("-- Pack Branding --").append("\n")
            append("Details:").append("\n")
            appendInfo(name != null, "Name", name)
            appendInfo(version != null, "Version", version)
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
