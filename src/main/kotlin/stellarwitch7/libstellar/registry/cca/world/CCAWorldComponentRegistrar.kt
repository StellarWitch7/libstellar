package stellarwitch7.libstellar.registry.cca.world

import net.minecraft.registry.RegistryKey
import net.minecraft.world.World
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import kotlin.reflect.KClass

abstract class CCAWorldComponentRegistrar : CCAComponentRegistrar<WorldComponentFactoryRegistry>, WorldComponentInitializer {
    override val registered: ArrayList<(WorldComponentFactoryRegistry) -> Unit> = ArrayList()

    fun <C : Component> register(name: String, c: KClass<C>, factory: (World) -> C, t: RegistryKey<World>?): ComponentKey<C> {
        return register(makeKey(name, c), factory, t)
    }

    fun <C : Component> register(key: ComponentKey<C>, factory: (World) -> C, t: RegistryKey<World>?): ComponentKey<C> {
        return register(key, { registry, key -> if (t == null) registry.register(key, factory) else registry.registerFor(t, key, factory) })
    }

    override fun registerWorldComponentFactories(registry: WorldComponentFactoryRegistry) {
        register(registry)
    }
}