package stellarwitch7.libstellar.registry

import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

internal interface RegUtil<T> {
    fun <V : T> register(id: Identifier, v: V): V {
        return Registry.register(registry(), id, v)
    }

    fun registry(): Registry<T>
}