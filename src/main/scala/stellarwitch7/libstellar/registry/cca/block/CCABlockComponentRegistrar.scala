package stellarwitch7.libstellar.registry.cca.block

import net.minecraft.block.entity.BlockEntity
import org.ladysnake.cca.api.v3.block.BlockComponentFactoryRegistry
import org.ladysnake.cca.api.v3.block.BlockComponentInitializer
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import scala.collection.mutable.ListBuffer
import org.ladysnake.cca.api.v3.component.ComponentFactory

abstract class CCABlockComponentRegistrar 
extends CCAComponentRegistrar[BlockComponentFactoryRegistry]
with BlockComponentInitializer {
  override val registered: ListBuffer[(BlockComponentFactoryRegistry) => Unit] = ListBuffer()

  def register[C <: Component, T <: BlockEntity](
    name: String,
    c: Class[C],
    factory: ComponentFactory[T, C],
    t: Class[T]
  ): ComponentKey[C] =
    register(makeKey(name, c), factory, t)

  def register[C <: Component, T <: BlockEntity](
    key: ComponentKey[C],
    factory: ComponentFactory[T, C],
    t: Class[T]
  ): ComponentKey[C] =
    register(key, _.registerFor(t, _, factory))

  override def registerBlockComponentFactories(
    registry: BlockComponentFactoryRegistry
  ): Unit =
    register(registry)
}
