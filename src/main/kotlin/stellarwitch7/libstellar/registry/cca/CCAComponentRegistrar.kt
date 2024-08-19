package stellarwitch7.libstellar.registry.cca

import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.ComponentKey

interface CCAComponentRegistrar<T> : CCAGenericComponentRegistrar {
    val registered: ArrayList<(T) -> Unit>

    fun <C : Component> register(key: ComponentKey<C>, callback: (T, ComponentKey<C>) -> Unit): ComponentKey<C> {
        registered.add { callback(it, key) }
        return key
    }

    fun register(registry: T) {
        for (fn in registered) {
            fn(registry)
        }
    }
}