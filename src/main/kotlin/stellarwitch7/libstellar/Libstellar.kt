package stellarwitch7.libstellar

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Libstellar : ModInitializer {
	val modID = "libstellar"
    val logger = LoggerFactory.getLogger(modID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("We're watching :3")
	}
}