package stellarwitch7.libstellar.registry.cca.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import scala.collection.mutable.ListBuffer
import org.ladysnake.cca.api.v3.component.ComponentFactory

abstract class CCAEntityComponentRegistrar
extends CCAComponentRegistrar[EntityComponentFactoryRegistry]
with EntityComponentInitializer {
  override val registered: ListBuffer[(EntityComponentFactoryRegistry) => Unit] = ListBuffer()

  def register[C <: Component, T <: Entity](
    name: String,
    c: Class[C],
    factory: ComponentFactory[T, C],
    t: Class[T]
  ): ComponentKey[C] =
    register(makeKey(name, c), factory, t)

  def register[C <: Component, T <: Entity](
    key: ComponentKey[C],
    factory: ComponentFactory[T, C],
    t: Class[T]
  ): ComponentKey[C] =
    register(key, _.registerFor(t, _, factory))

  def register[C <: Component](
    name: String,
    c: Class[C],
    factory: ComponentFactory[PlayerEntity, C],
    copyStrat: RespawnCopyStrategy[Component]
  ): ComponentKey[C] =
    register(makeKey(name, c), factory, copyStrat)

  def register[C <: Component](
    key: ComponentKey[C],
    factory: ComponentFactory[PlayerEntity, C],
    copyStrat: RespawnCopyStrategy[Component]
  ): ComponentKey[C] =
    register(key, _.registerForPlayers(_, factory, copyStrat))

  override def registerEntityComponentFactories(
    registry: EntityComponentFactoryRegistry
  ): Unit =
    register(registry)
}
