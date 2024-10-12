package stellarwitch7.libstellar.ritual

import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.registry.Registry
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.Libstellar
import stellarwitch7.libstellar.util.StellarCodecUtils
import stellarwitch7.libstellar.registry.codec.CodecRegistrar
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.registry.codec.CodecTypeProvider
import stellarwitch7.libstellar.ritual.step.Step
import scala.collection.mutable.ArrayDeque

/**
 * A multi-step, multi-block "ritual" process.
 * @param pos the position that this ritual is centred on.
 * @param _steps the steps this ritual executes when calling `advance`.
 */
abstract class Ritual(val pos: BlockPos, private var _steps: ArrayDeque[Step]) extends CodecTypeProvider[Ritual] {
  /**
   * This ritual's next steps.
   */
  def steps = _steps

  /**
   * Initialises the ritual with the default queue of steps.
   * @param world the world this ritual is in.
   * @return this ritual.
   */
  def init(world: ServerWorld): Ritual

  /**
   * Applies this ritual's failure case.
   * This is used internally by `advance` when a step fails and typically should not be called by implementors.
   * @param world the world this ritual is in.
   */
  protected def fail(world: ServerWorld): Unit

  /**
   * Executes the next step in the ritual.
   * @param world the world this ritual is in.
   * @return whether the ritual is complete or not.
   */
  def advance(world: ServerWorld): Boolean = steps.headOption
    .map(_.apply(world, this, steps))
    .map(b =>
      if b then
        false
      else
        fail(world)
        true)
    .getOrElse(true)
}

object Ritual extends CodecRegistrar[Ritual] {
  override val modID = Libstellar.MOD_ID
  override val registry = makeReg("ritual")

  /**
   * Registers a new ritual with the default codec, for convenience.
   * @param name the path ID of the ritual.
   * @param constructor the ritual's constructor.
   * @return the ritual's codec type.
   */
  def register[T <: Ritual](name: String, constructor: (BlockPos, ArrayDeque[Step]) => T): CodecType[Ritual] =
    register(name, RecordCodecBuilder.mapCodec[T](builder => builder.group(
      BlockPos.CODEC.fieldOf("pos").forGetter(_.pos),
      StellarCodecUtils.queueCodec(Step.codec).fieldOf("steps").forGetter(_.steps)
    ).apply(builder, constructor(_, _))))
}
