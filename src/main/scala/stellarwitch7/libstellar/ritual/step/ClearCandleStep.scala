package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.CandleBlock
import net.minecraft.particle.ParticleTypes
import net.minecraft.registry.tag.BlockTags
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque

/**
 * Succeeds if there is a lit candle at the given offset from the ritual centre. The candle is blown out.
 * @param offset the offset from the ritual centre to check for a lit candle.
 */
class ClearCandleStep(private val offset: BlockPos) extends Step {
  override val `type`: CodecType[Step] = Step.clearCandle

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    val pos = ritual.pos.add(offset)
    val state = world.getBlockState(pos)

    if state.isIn(BlockTags.CANDLES) && state.get(CandleBlock.LIT) then {
      world.setBlockState(pos, state.`with`(CandleBlock.LIT, false))

      val vec = pos.toCenterPos()
      world.spawnParticles(ParticleTypes.SOUL,
        vec.x, vec.y, vec.z,
        9, 0.2, 0.2, 0.2, 0.0
      )

      return true
    }

    false
  }
}

object ClearCandleStep {
  val codec: MapCodec[ClearCandleStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
    BlockPos.CODEC.fieldOf("offset").forGetter(_.offset)
  ).apply(builder, ClearCandleStep(_)))
}
