package stellarwitch7.libstellar.utils

import com.mojang.serialization.Codec
import stellarwitch7.libstellar.ritual.step.Step

object RitualCodecUtils {
    /**
     * A codec for serializing a ritual's step queue as a list.
     */
    val steps: Codec<ArrayDeque<Step>> = Step.codec.codec().listOf().xmap(::ArrayDeque, ArrayDeque<Step>::toList)
}