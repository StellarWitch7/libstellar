package stellarwitch7.libstellar.block

import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.RegUtil

object BlockRegUtil : RegUtil<Block> {
    override fun registry(): Registry<Block> {
        return Registries.BLOCK
    }
}