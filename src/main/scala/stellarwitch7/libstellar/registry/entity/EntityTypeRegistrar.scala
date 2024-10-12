package stellarwitch7.libstellar.registry.entity

import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

trait EntityTypeRegistrar extends Registrar[EntityType[?]] {
  override val registry: Registry[EntityType[?]] = Registries.ENTITY_TYPE
}
