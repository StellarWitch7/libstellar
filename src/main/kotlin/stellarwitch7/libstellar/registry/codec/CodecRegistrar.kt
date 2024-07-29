package stellarwitch7.libstellar.registry.codec

import com.mojang.serialization.Lifecycle
import com.mojang.serialization.MapCodec
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier
import stellarwitch7.libstellar.registry.Registrar

interface CodecRegistrar<T : CodecTypeProvider<T>> : Registrar<CodecType<T>> {
    val name: String

    //TODO: any way to improve performance?
    val codec: MapCodec<T>
        get() = registry.codec.dispatchMap(CodecTypeProvider<T>::type, CodecType<T>::codec)

    val regKey: RegistryKey<Registry<CodecType<T>>>
        get() {
            return RegistryKey.ofRegistry(id(name))
        }

    fun makeReg(): Registry<CodecType<T>> = SimpleRegistry(regKey, Lifecycle.stable())

    fun <V : T> register(name: String, codec: MapCodec<out V>): CodecType<T> {
        return register(name, CodecType(codec))
    }
}