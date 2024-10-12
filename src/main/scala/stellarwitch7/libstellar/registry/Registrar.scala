package stellarwitch7.libstellar.registry

import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

trait Registrar[T] {
  val modID: String
  val registry: Registry[T]

  def id(name: String): Identifier = Identifier.of(modID, name)
  def register(): Unit = { }
  def register[V <: T](name: String, value: V): V = Registry.register(registry, id(name), value)
}
