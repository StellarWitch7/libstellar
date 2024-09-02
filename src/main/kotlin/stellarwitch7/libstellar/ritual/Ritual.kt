package stellarwitch7.libstellar.ritual

import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.registry.Registry
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.Libstellar
import stellarwitch7.libstellar.registry.codec.CodecRegistrar
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.registry.codec.CodecTypeProvider
import stellarwitch7.libstellar.ritual.step.Step
import stellarwitch7.libstellar.utils.KCodecUtils

/**
 * A multi-step, multi-block "ritual" process.
 * @param pos the position that this ritual is centred on.
 * @param steps the steps this ritual executes when calling `advance`.
 */
abstract class Ritual(val pos: BlockPos, steps: ArrayDeque<Step>) : CodecTypeProvider<Ritual> {
    /**
     * This ritual's next steps.
     */
    var steps: ArrayDeque<Step> = steps
        protected set

    /**
     * Initialises the ritual with the default queue of steps.
     * @param world the world this ritual is in.
     * @return this ritual.
     */
    abstract fun init(world: ServerWorld): Ritual

    /**
     * Applies this ritual's failure case.
     * This is used internally by `advance` when a step fails and typically should not be called by implementors.
     * @param world the world this ritual is in.
     */
    protected abstract fun fail(world: ServerWorld)

    /**
     * Executes the next step in the ritual.
     * @param world the world this ritual is in.
     * @return whether the ritual is complete or not.
     */
    fun advance(world: ServerWorld): Boolean {
        return when (steps.removeFirstOrNull()?.apply(world, this, steps)) {
            true -> false
            false -> { fail(world); true }
            null -> true
        }
    }

    companion object : CodecRegistrar<Ritual> {
        override val modID: String = Libstellar.MOD_ID
        override val registry: Registry<CodecType<Ritual>> = makeReg("ritual")

        /**
         * Registers a new ritual with the default codec, for convenience.
         * @param name the path ID of the ritual.
         * @param constructor the ritual's constructor.
         * @return the ritual's codec type.
         */
        fun <T : Ritual> register(name: String, constructor: (BlockPos, ArrayDeque<Step>) -> T): CodecType<Ritual> {
            return register(name, RecordCodecBuilder.mapCodec { builder ->
                builder.group(
                    BlockPos.CODEC.fieldOf("pos").forGetter(Ritual::pos),
                    KCodecUtils.queueCodec(Step.codec).fieldOf("steps").forGetter(Ritual::steps)
                ).apply(builder, constructor)
            })
        }
    }
}