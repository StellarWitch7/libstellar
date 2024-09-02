package stellarwitch7.libstellar.registry.codec

import com.mojang.serialization.Lifecycle
import com.mojang.serialization.MapCodec
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import stellarwitch7.libstellar.registry.Registrar

interface CodecRegistrar<T : CodecTypeProvider<T>> : Registrar<CodecType<T>> {
    val codec: MapCodec<T>
        get() = registry.codec.dispatchMap(CodecTypeProvider<T>::type, CodecType<T>::codec)

    fun makeReg(name: String): Registry<CodecType<T>> = SimpleRegistry(RegistryKey.ofRegistry(id(name)), Lifecycle.stable())

    fun <V : T> register(name: String, codec: MapCodec<out V>): CodecType<T> {
        return register(name, CodecType(codec))
    }
}