package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque

/**
 * Delays the execution of the next steps by pushing itself to the queue with a decremented counter.
 * @param ticks the amount of ticks to sleep for.
 */
class SleepStep(val ticks: Int) extends Step {
  override val `type`: CodecType[Step] = Step.sleep

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean =
    if ticks > 1 then
      steps.prepend(SleepStep(ticks - 1))
    end if

    true
}

object SleepStep {
  val codec: MapCodec[SleepStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
    Codec.INT.fieldOf("ticks").forGetter(_.ticks)
  ).apply(builder, SleepStep(_)))
}
