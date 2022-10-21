package xyz.deftu.crashbrander

import org.apache.logging.log4j.LogManager
import java.io.File

object CrashBranderConstants {
    @JvmStatic val logger = LogManager.getLogger("@MOD_NAME@")
    @JvmStatic val debug = System.getProperty("@MOD_ID@.debug", "false") == "true"

    fun getConfigDirectory(): File {
        val dir = File("config")
        if (!dir.exists() && !dir.mkdirs())
            throw IllegalStateException("Could not create config directory!")

        return dir
    }
}
