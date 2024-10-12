package stellarwitch7.libstellar.registry.owo.net

import io.wispforest.owo.network.OwoNetChannel
import net.minecraft.util.Identifier

trait OwoPacketRegistrar {
  val modID: String
  val name: String
  val channel: OwoNetChannel

  def id(name: String): Identifier = Identifier.of(modID, name)
  def makeChannel(): OwoNetChannel = OwoNetChannel.create(id(name))

  def registerServerbound[T <: OwoPacketServerbound[T] & Record](packet: Class[T]): Unit = {
    channel.registerServerbound(packet, _.handle(_))
  }

  def register(): Unit
}
