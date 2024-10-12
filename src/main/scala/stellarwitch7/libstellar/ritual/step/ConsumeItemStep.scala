package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.item.Item
import net.minecraft.particle.ParticleTypes
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.TypeFilter
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque
import scala.collection.JavaConverters._

/**
 * Consumes an amount of items of the specified type in a sphere around the ritual centre.
 * @param item the item type to consume.
 * @param radius the distance from the centre of the ritual where items may be consumed.
 * @param count the amount of items needed for success.
 */
class ConsumeItemStep(private val item: Item, private val radius: Double = 0.0, private var count: Int = 1) extends Step {
  override val `type`: CodecType[Step] = Step.consumeItem

  //TODO: add a way to make consumed items pop back out?
  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    if count < 1 then return true

    val offset1 = Vec3d(radius, radius, radius)
    val offset2 = offset1.negate()
    val entities = world.getEntitiesByType(TypeFilter.instanceOf(classOf[ItemEntity]),
      Box(ritual.pos.toCenterPos().add(offset1), ritual.pos.toCenterPos().add(offset2)),
      _.getStack().isOf(item))

    for (entity <- entities.asScala) {
      if (entity.getStack().getCount() > count) {
        entity.getStack().decrement(count)
        return true
      } else if (entity.getStack().getCount() == count) {
        discard(world, entity)
        return true
      } else {
        count -= entity.getStack().getCount()
        discard(world, entity)
      }
    }

    false
  }

  private def discard(world: ServerWorld, entity: Entity): Unit = {
    entity.discard()
    world.spawnParticles(ParticleTypes.POOF,
      entity.getX(), entity.getY(), entity.getZ(),
      1, 0.0, 0.0, 0.0, 0.0
    )
  }
}

object ConsumeItemStep {
  val codec: MapCodec[ConsumeItemStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
    Registries.ITEM.getCodec().fieldOf("item").forGetter(_.item),
    Codec.DOUBLE.fieldOf("radius").forGetter(_.radius),
    Codec.INT.fieldOf("count").forGetter(_.count)
  ).apply(builder, ConsumeItemStep(_, _, _)))
}
