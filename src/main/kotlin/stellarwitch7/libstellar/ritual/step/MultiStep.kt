package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step.Companion.multi
import stellarwitch7.libstellar.utils.KCodecUtils

/**
 * Executes multiple steps as a separate queue with its own rate of execution.
 * @param steps the steps to execute.
 * @param executionLimit the amount of steps to execute in a tick before pushing the rest to the queue.
 */
class MultiStep(val steps: ArrayDeque<Step>, val executionLimit: Int = 20) : Step {
    override val type: CodecType<Step> = multi

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        for (i in (0).rangeTo(executionLimit)) {
            when (this.steps.removeFirstOrNull()?.apply(world, ritual, this.steps)) {
                true -> { }
                false -> return false
                null -> return true
            }
        }

        if (this.steps.size > 0)
            steps.addFirst(this)

        return true
    }

    companion object {
        // Must be as lazy as possible due to static init nonsense
        val codec: MapCodec<MultiStep> = KCodecUtils.lazyMapCodec {
            RecordCodecBuilder.mapCodec { builder ->
                builder.group(
                    Codec.lazyInitialized { KCodecUtils.queueCodec(Step.codec) }.fieldOf("steps").forGetter(MultiStep::steps),
                    Codec.INT.fieldOf("execution_limit").forGetter(MultiStep::executionLimit)
                ).apply(builder, ::MultiStep)
            }
        }
    }
}