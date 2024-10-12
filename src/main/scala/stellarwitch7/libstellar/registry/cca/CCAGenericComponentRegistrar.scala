package stellarwitch7.libstellar.registry.cca

import net.minecraft.util.Identifier
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry

trait CCAGenericComponentRegistrar {
    val modID: String

    def id(name: String): Identifier = Identifier.of(modID, name)
    def makeKey[C <: Component](name: String, c: Class[C]): ComponentKey[C] = ComponentRegistry.getOrCreate(id(name), c);
}
