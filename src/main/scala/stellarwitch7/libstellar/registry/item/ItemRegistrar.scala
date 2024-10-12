package stellarwitch7.libstellar.registry.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import stellarwitch7.libstellar.registry.Registrar

trait ItemRegistrar extends Registrar[Item] {
  override val registry: Registry[Item] = Registries.ITEM
}
