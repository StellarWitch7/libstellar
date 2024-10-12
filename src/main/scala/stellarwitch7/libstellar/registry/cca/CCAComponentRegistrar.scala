package stellarwitch7.libstellar.registry.cca

import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import scala.collection.mutable.ListBuffer

trait CCAComponentRegistrar[T] extends CCAGenericComponentRegistrar {
  val registered: ListBuffer[(T) => Unit]

  def register[C <: Component](key: ComponentKey[C], callback: (T, ComponentKey[C]) => Unit): ComponentKey[C] =
    registered.append(callback(_, key))
    key

  def register(registry: T): Unit =
    registered.foreach(_(registry))
}
