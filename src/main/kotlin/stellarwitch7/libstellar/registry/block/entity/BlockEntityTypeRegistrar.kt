package stellarwitch7.libstellar.registry.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

interface BlockEntityTypeRegistrar : Registrar<BlockEntityType<*>> {
    override val registry: Registry<BlockEntityType<*>>
        get() = Registries.BLOCK_ENTITY_TYPE
}