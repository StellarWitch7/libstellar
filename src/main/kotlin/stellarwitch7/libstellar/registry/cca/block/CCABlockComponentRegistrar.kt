package stellarwitch7.libstellar.registry.cca.block

import net.minecraft.block.entity.BlockEntity
import org.ladysnake.cca.api.v3.block.BlockComponentFactoryRegistry
import org.ladysnake.cca.api.v3.block.BlockComponentInitializer
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import kotlin.reflect.KClass

abstract class CCABlockComponentRegistrar : CCAComponentRegistrar<BlockComponentFactoryRegistry>, BlockComponentInitializer {
    override val registered: ArrayList<(BlockComponentFactoryRegistry) -> Unit> = ArrayList()

    fun <C : Component, T : BlockEntity> register(name: String, c: KClass<C>, factory: (BlockEntity) -> C, t: KClass<T>): ComponentKey<C> {
        val result = makeKey(name, c)
        registered.add { registry -> registry.registerFor(t.java, result, factory) }
        return result
    }

    override fun registerBlockComponentFactories(registry: BlockComponentFactoryRegistry) {
        register(registry)
    }
}