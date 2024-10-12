package stellarwitch7.libstellar

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step

object Libstellar extends ModInitializer {
  final val MOD_ID = "libstellar"
  final val LOGGER = LoggerFactory.getLogger(MOD_ID)

  override def onInitialize(): Unit = {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    LOGGER.info("We're watching :3")
    Step.register()
    Ritual.register()
  }
}
