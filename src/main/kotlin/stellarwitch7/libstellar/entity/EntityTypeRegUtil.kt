package stellarwitch7.libstellar.entity

import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.RegUtil

object EntityTypeRegUtil : RegUtil<EntityType<*>> {
    override fun registry(): Registry<EntityType<*>> {
        return Registries.ENTITY_TYPE
    }
}