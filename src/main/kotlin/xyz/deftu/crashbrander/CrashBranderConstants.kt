package xyz.deftu.crashbrander

import org.apache.logging.log4j.LogManager

object CrashBranderConstants {
    @JvmStatic val logger = LogManager.getLogger("@MOD_NAME@")
    @JvmStatic val debug = System.getProperty("@MOD_ID@.debug", "false") == "true"
}
