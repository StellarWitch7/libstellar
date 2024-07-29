package stellarwitch7.libstellar.codec

import com.mojang.serialization.Lifecycle
import com.mojang.serialization.MapCodec
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier

interface CodecRegistry<T> {
    val registry: Registry<CodecType<T>>
    val factory: (MapCodec<out T>) -> CodecType<T>
    val name: String
    val namespace: String

    val regKey: RegistryKey<Registry<CodecType<T>>>
        get() {
            return RegistryKey.ofRegistry(id(name))
        }

    fun makeReg(): Registry<CodecType<T>> = SimpleRegistry(regKey, Lifecycle.stable())

    fun id(name: String): Identifier {
        return Identifier.of(namespace, name)
    }

    fun <V : T> register(name: String, codec: MapCodec<out V>): CodecType<T> {
        return Registry.register(registry, id(name), factory(codec))
    }
}