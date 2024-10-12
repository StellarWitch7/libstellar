package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque

/**
 * Drops the given stack at an offset from the ritual centre. Always succeeds.
 * @param stack the item stack to drop.
 * @param offset the offset from the ritual centre to drop the items.
 */
class DropItemStep(val stack: ItemStack, val offset: BlockPos) extends  Step {
  override val `type`: CodecType[Step] = Step.dropItem

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    val offsetPos = ritual.pos.add(offset)
    world.spawnParticles(ParticleTypes.FLASH,
      offsetPos.getX(), offsetPos.getY(), offsetPos.getZ(),
      1, 0.0, 0.0, 0.0, 0.0)
    world.spawnEntity(ItemEntity(world,
      offsetPos.getX(),
      offsetPos.getY(),
      offsetPos.getZ(),
      stack))

    true
  }
}

object DropItemStep {
  val codec: MapCodec[DropItemStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
      ItemStack.CODEC.fieldOf("stack").forGetter(_.stack),
      BlockPos.CODEC.fieldOf("offset").forGetter(_.offset)
    ).apply(builder, DropItemStep(_, _))
  )
}
