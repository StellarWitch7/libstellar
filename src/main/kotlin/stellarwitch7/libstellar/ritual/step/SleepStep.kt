package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step.Companion.sleep

/**
 * Delays the execution of the next steps by pushing itself to the queue with a decremented counter.
 * @param ticks the amount of ticks to sleep for.
 */
class SleepStep(val ticks: Int) : Step {
    override val type: CodecType<Step> = sleep

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        if (ticks > 1)
            steps.addFirst(SleepStep(ticks - 1))

        return true
    }

    companion object {
        val codec: MapCodec<SleepStep> = RecordCodecBuilder.mapCodec { builder ->
            builder.group(
                Codec.INT.fieldOf("ticks").forGetter(SleepStep::ticks)
            ).apply(builder, ::SleepStep)
        }
    }
}