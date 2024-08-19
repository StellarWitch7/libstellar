package stellarwitch7.libstellar.registry.cca

import net.minecraft.util.Identifier
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry
import kotlin.reflect.KClass

interface CCAGenericComponentRegistrar {
    val modID: String

    fun id(name: String): Identifier {
        return Identifier.of(modID, name)
    }

    fun <C : Component> makeKey(name: String, c: KClass<C>): ComponentKey<C> {
        return ComponentRegistry.getOrCreate(id(name), c.java);
    }
}