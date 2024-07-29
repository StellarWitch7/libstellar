package stellarwitch7.libstellar.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.RegUtil

object BlockEntityTypeRegUtil : RegUtil<BlockEntityType<*>> {
    override fun registry(): Registry<BlockEntityType<*>> {
        return Registries.BLOCK_ENTITY_TYPE
    }
}