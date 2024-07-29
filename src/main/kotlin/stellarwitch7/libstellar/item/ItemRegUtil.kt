package stellarwitch7.libstellar.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.RegUtil

object ItemRegUtil : RegUtil<Item> {
    override fun registry(): Registry<Item> {
        return Registries.ITEM
    }
}