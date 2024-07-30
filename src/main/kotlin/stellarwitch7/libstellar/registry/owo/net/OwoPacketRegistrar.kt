package stellarwitch7.libstellar.registry.owo.net

import io.wispforest.owo.network.OwoNetChannel
import net.minecraft.util.Identifier
import kotlin.reflect.KClass

interface OwoPacketRegistrar {
    val modID: String
    val name: String
    val channel: OwoNetChannel

    fun id(name: String): Identifier {
        return Identifier.of(modID, name)
    }

    fun makeChannel(): OwoNetChannel {
        return OwoNetChannel.create(id(name))
    }

    fun <T> registerServerbound(packet: KClass<T>) where T : OwoPacketServerbound<T>, T : Record {
        channel.registerServerbound(packet.java, OwoPacketServerbound<T>::handle)
    }

    fun register()
}