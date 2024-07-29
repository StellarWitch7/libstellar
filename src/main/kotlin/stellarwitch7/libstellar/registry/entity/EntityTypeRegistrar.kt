package stellarwitch7.libstellar.registry.entity

import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

interface EntityTypeRegistrar : Registrar<EntityType<*>> {
    override val registry: Registry<EntityType<*>>
        get() = Registries.ENTITY_TYPE
}