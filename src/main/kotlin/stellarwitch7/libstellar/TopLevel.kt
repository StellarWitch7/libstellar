package stellarwitch7.libstellar

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import stellarwitch7.libstellar.block.BlockRegUtil
import stellarwitch7.libstellar.block.entity.BlockEntityTypeRegUtil
import stellarwitch7.libstellar.entity.EntityTypeRegUtil
import stellarwitch7.libstellar.item.ItemRegUtil

fun <T : Item> register(id: Identifier, item: T): T {
    return ItemRegUtil.register(id, item)
}

fun <T : Block> register(id: Identifier, block: T): T {
    return BlockRegUtil.register(id, block)
}

fun <T : EntityType<*>> register(id: Identifier, entityType: T): T {
    return EntityTypeRegUtil.register(id, entityType)
}

fun <T : BlockEntityType<*>> register(id: Identifier, blockEntityType: T): T {
    return BlockEntityTypeRegUtil.register(id, blockEntityType)
}