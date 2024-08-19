package stellarwitch7.libstellar.registry.cca.chunk

import net.minecraft.world.chunk.Chunk
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import kotlin.reflect.KClass

abstract class CCAChunkComponentRegistrar : CCAComponentRegistrar<ChunkComponentFactoryRegistry>, ChunkComponentInitializer {
    override val registered: ArrayList<(ChunkComponentFactoryRegistry) -> Unit> = ArrayList()

    fun <C : Component> register(name: String, c: KClass<C>, factory: (Chunk) -> C): ComponentKey<C> {
        val result = makeKey(name, c)
        registered.add { registry -> registry.register(result, factory) }
        return result
    }

    override fun registerChunkComponentFactories(registry: ChunkComponentFactoryRegistry) {
        register(registry)
    }
}