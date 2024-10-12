package stellarwitch7.libstellar.registry.cca.chunk

import net.minecraft.world.chunk.Chunk
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import stellarwitch7.libstellar.registry.cca.CCAComponentRegistrar
import scala.collection.mutable.ListBuffer
import org.ladysnake.cca.api.v3.component.ComponentFactory

abstract class CCAChunkComponentRegistrar
extends CCAComponentRegistrar[ChunkComponentFactoryRegistry]
with ChunkComponentInitializer {
  override val registered: ListBuffer[(ChunkComponentFactoryRegistry) => Unit] = ListBuffer()

  def register[C <: Component](
    name: String,
    c: Class[C],
    factory: ComponentFactory[Chunk, C]
  ): ComponentKey[C] =
    register(makeKey(name, c), factory)

  def register[C <: Component](
    key: ComponentKey[C],
    factory: ComponentFactory[Chunk, C]
  ): ComponentKey[C] =
    register(key, _.register(_, factory))

  override def registerChunkComponentFactories(
    registry: ChunkComponentFactoryRegistry
  ): Unit =
    register(registry)
}
