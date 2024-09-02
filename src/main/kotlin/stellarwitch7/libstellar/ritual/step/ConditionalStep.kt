package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step.Companion.conditional
import stellarwitch7.libstellar.utils.KCodecUtils

/**
 * Executes a step, and pushes one of two other steps depending on the result.
 * @param condition the step that is run to determine the result.
 * @param then the step that is pushed if the condition succeeds.
 * @param otherwise the step that is pushed if the condition fails.
 */
class ConditionalStep(val condition: Step, val then: Step, val otherwise: Step) : Step {
    override val type: CodecType<Step> = conditional

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        if (condition.apply(world, ritual, steps))
            steps.addFirst(then)
        else steps.addFirst(otherwise)

        return true
    }

    companion object {
        val codec: MapCodec<ConditionalStep> = KCodecUtils.lazyMapCodec {
            RecordCodecBuilder.mapCodec { builder ->
                builder.group(
                    Step.codec.fieldOf("condition").forGetter(ConditionalStep::condition),
                    Step.codec.fieldOf("then").forGetter(ConditionalStep::then),
                    Step.codec.fieldOf("otherwise").forGetter(ConditionalStep::otherwise)
                ).apply(builder, ::ConditionalStep)
            }
        }
    }
}