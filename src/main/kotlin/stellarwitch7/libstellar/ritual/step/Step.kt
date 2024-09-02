package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import net.minecraft.registry.Registry
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.Libstellar
import stellarwitch7.libstellar.registry.codec.CodecRegistrar
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.registry.codec.CodecTypeProvider
import stellarwitch7.libstellar.ritual.Ritual

/**
 * A step in a ritual. May check for requirements, consume items, etc.
 */
interface Step : CodecTypeProvider<Step> {
    /**
     * Applies this step to the given ritual.
     * @param world the world the ritual is in.
     * @param ritual the ritual this step belongs to.
     * @param steps the steps following this one.
     * @return whether the step succeeded or not. Callers should use this to determine whether the next step should be run.
     */
    fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean

    companion object : CodecRegistrar<Step> {
        override val modID: String = Libstellar.MOD_ID
        override val registry: Registry<CodecType<Step>> = makeReg("step")

        val sleep: CodecType<Step> = register("sleep", SleepStep.codec)
        val multi: CodecType<Step> = register("multi", MultiStep.codec)
        val conditional: CodecType<Step> = register("conditional", ConditionalStep.codec)
        val clearCandle: CodecType<Step> = register("clear_candle", ClearCandleStep.codec)
        val consumeItem: CodecType<Step> = register("consume_item", ConsumeItemStep.codec)
        val dropItem: CodecType<Step> = register("drop_item", DropItemStep.codec)
        val summonEntity: CodecType<Step> = register("summon_entity", SummonEntityStep.codec)

        /**
         * A codec for serializing a ritual's step queue as a list.
         */
        val queueCodec: Codec<ArrayDeque<Step>> = Step.codec.listOf().xmap(::ArrayDeque, ArrayDeque<Step>::toList)
    }
}