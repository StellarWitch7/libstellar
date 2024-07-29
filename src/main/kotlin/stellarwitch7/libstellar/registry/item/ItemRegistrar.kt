package stellarwitch7.libstellar.registry.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

interface ItemRegistrar : Registrar<Item> {
    override val registry: Registry<Item>
        get() = Registries.ITEM
}