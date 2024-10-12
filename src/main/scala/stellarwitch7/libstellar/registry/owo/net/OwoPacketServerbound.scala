package stellarwitch7.libstellar.registry.owo.net

import io.wispforest.owo.network.ServerAccess

trait OwoPacketServerbound[T <: OwoPacketServerbound[T] & Record] extends OwoPacket[T] {
  def handle(access: ServerAccess): Unit
}
