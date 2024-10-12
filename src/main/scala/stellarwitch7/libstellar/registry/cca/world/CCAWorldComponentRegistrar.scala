package stellarwitch7.libstellar.registry.cca.world

import net.minecraft.registry.RegistryKey
import net.minecraft.world.World
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import org.ladysnake.cca.api.v3.component.ComponentFactory
import scala.collection.mutable.ListBuffer

abstract class CCAWorldComponentRegistrar
extends CCAComponentRegistrar[WorldComponentFactoryRegistry]
with WorldComponentInitializer {
  override val registered: ListBuffer[(WorldComponentFactoryRegistry) => Unit] = ListBuffer()

  def register[C <: Component](
    name: String,
    c: Class[C],
    factory: ComponentFactory[World, C],
    world: Option[RegistryKey[World]]
  ): ComponentKey[C] =
    register(makeKey(name, c), factory, world)

  def register[C <: Component](
    key: ComponentKey[C],
    factory: ComponentFactory[World, C],
    world: Option[RegistryKey[World]]
  ): ComponentKey[C] =
    register(key, world
      .map(world => (registry: WorldComponentFactoryRegistry, k: ComponentKey[C]) => registry.registerFor(world, k, factory))
      .getOrElse((registry: WorldComponentFactoryRegistry, k: ComponentKey[C]) => registry.register(k, factory)))

  override def registerWorldComponentFactories(
    registry: WorldComponentFactoryRegistry
  ): Unit =
    register(registry)
}
