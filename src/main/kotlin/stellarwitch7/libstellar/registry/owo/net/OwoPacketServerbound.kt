package stellarwitch7.libstellar.registry.owo.net

import io.wispforest.owo.network.ServerAccess

interface OwoPacketServerbound<T> : OwoPacket<T> where T : OwoPacketServerbound<T>, T : Record {
    fun handle(access: ServerAccess)
}