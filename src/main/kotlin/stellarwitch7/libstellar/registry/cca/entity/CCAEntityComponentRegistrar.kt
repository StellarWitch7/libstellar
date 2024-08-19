package stellarwitch7.libstellar.registry.cca.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import kotlin.reflect.KClass

abstract class CCAEntityComponentRegistrar : CCAComponentRegistrar<EntityComponentFactoryRegistry>, EntityComponentInitializer {
    override val registered: ArrayList<(EntityComponentFactoryRegistry) -> Unit> = ArrayList()

    fun <C : Component, T : Entity> register(name: String, c: KClass<C>, factory: (Entity) -> C, t: KClass<T>): ComponentKey<C> {
        return register(makeKey(name, c), factory, t)
    }

    fun <C : Component, T : Entity> register(key: ComponentKey<C>, factory: (Entity) -> C, t: KClass<T>): ComponentKey<C> {
        return register(key, { registry, key -> registry.registerFor(t.java, key, factory) })
    }

    fun <C : Component> register(name: String, c: KClass<C>, factory: (PlayerEntity) -> C, copyStrat: RespawnCopyStrategy<Component>): ComponentKey<C> {
        return register(makeKey(name, c), factory, copyStrat)
    }

    fun <C : Component> register(key: ComponentKey<C>, factory: (PlayerEntity) -> C, copyStrat: RespawnCopyStrategy<Component>): ComponentKey<C> {
        return register(key, { registry, key -> registry.registerForPlayers(key, factory, copyStrat) })
    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        register(registry)
    }
}