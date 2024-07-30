package stellarwitch7.libstellar.registry.cca

import net.minecraft.util.Identifier
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry
import kotlin.reflect.KClass

interface CCAComponentRegistrar<T> {
    val modID: String
    val registered: ArrayList<(T) -> Unit>

    fun id(name: String): Identifier {
        return Identifier.of(modID, name)
    }

    fun <C : Component> makeKey(name: String, c: KClass<C>): ComponentKey<C> {
        val result = ComponentRegistry.getOrCreate(id(name), c.java)
        return result;
    }

    fun register(registry: T) {
        for (fn in registered) {
            fn(registry)
        }
    }
}