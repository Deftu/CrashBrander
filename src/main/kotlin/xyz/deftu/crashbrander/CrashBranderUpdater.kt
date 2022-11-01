package xyz.deftu.crashbrander

import xyz.deftu.crashbrander.config.Config
import xyz.deftu.lib.updater.UpdaterEntrypoint

object CrashBranderUpdater : UpdaterEntrypoint {
    override fun shouldCheck() = Config.INSTANCE.updateChecking
}
