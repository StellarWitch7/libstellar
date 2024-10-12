package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.util.StellarCodecUtils
import scala.collection.mutable.ArrayDeque

/**
 * Executes a step, and pushes one of two other steps depending on the result.
 * @param condition the step that is run to determine the result.
 * @param then the step that is pushed if the condition succeeds.
 * @param otherwise the step that is pushed if the condition fails.
 */
class ConditionalStep(val condition: Step, val `then`: Step, val otherwise: Step) extends Step {
  override val `type`: CodecType[Step] = Step.conditional

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    if condition.apply(world, ritual, steps) then
      steps.prepend(`then`)
    else steps.prepend(otherwise)

    true
  }
}

object ConditionalStep {
  val codec: MapCodec[ConditionalStep] = StellarCodecUtils.lazyMapCodec(() =>
    RecordCodecBuilder.mapCodec(builder => builder.group(
        Step.codec.fieldOf("condition").forGetter(_.condition),
        Step.codec.fieldOf("then").forGetter(_.`then`),
        Step.codec.fieldOf("otherwise").forGetter(_.otherwise)
      ).apply(builder, ConditionalStep(_, _, _))
    )
  )
}
