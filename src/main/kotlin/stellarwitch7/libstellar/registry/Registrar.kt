package stellarwitch7.libstellar.registry

import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

interface Registrar<T> {
    val modID: String
    val registry: Registry<T>

    fun id(name: String): Identifier {
        return Identifier.of(modID, name)
    }

    fun register() {
    }

    fun <V : T> register(name: String, value: V): V {
        return Registry.register(registry, id(name), value)
    }
}