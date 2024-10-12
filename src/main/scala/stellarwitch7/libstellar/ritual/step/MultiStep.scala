package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.util.StellarCodecUtils
import scala.collection.mutable.ArrayDeque

/**
 * Executes multiple steps as a separate queue with its own rate of execution.
 * @param steps the steps to execute.
 * @param executionLimit the amount of steps to execute in a tick before pushing the rest to the queue.
 */
class MultiStep(val steps: ArrayDeque[Step], val executionLimit: Int = 20) extends Step {
  override val `type`: CodecType[Step] = Step.multi

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    for (i <- 0 to executionLimit) {
      this.steps.headOption.map(_.apply(world, ritual, this.steps)) match
        case Some(value) => if !value then return false
        case None => return true
    }

    if this.steps.size > 0 then
      steps.prepend(this)
    end if

    true
  }
}

object MultiStep {
  val codec: MapCodec[MultiStep] = StellarCodecUtils.lazyMapCodec(() =>
    RecordCodecBuilder.mapCodec(builder => builder.group(
      StellarCodecUtils.queueCodec(Step.codec).fieldOf("steps").forGetter(_.steps),
      Codec.INT.fieldOf("execution_limit").forGetter(_.executionLimit)
    ).apply(builder, MultiStep(_, _)))
  )
}
