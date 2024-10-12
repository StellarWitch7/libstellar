package stellarwitch7.libstellar.registry.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

trait BlockEntityTypeRegistrar extends Registrar[BlockEntityType[?]] {
    override val registry: Registry[BlockEntityType[?]] = Registries.BLOCK_ENTITY_TYPE
}
