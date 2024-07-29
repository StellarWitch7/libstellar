package stellarwitch7.libstellar.registry.block

import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

interface BlockRegistrar : Registrar<Block> {
    override val registry: Registry<Block>
        get() = Registries.BLOCK
}