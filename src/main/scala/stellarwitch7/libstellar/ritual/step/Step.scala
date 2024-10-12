package stellarwitch7.libstellar.ritual.step

import net.minecraft.registry.Registry
import net.minecraft.server.world.ServerWorld
import stellarwitch7.libstellar.Libstellar
import stellarwitch7.libstellar.registry.codec.CodecRegistrar
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.registry.codec.CodecTypeProvider
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque

/**
 * A step in a ritual. May check for requirements, consume items, etc.
 */
trait Step extends CodecTypeProvider[Step] {
  /**
   * Applies this step to the given ritual.
   * @param world the world the ritual is in.
   * @param ritual the ritual this step belongs to.
   * @param steps the steps following this one.
   * @return whether the step succeeded or not. Callers should use this to determine whether the next step should be run.
   */
  def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean
}

object Step extends CodecRegistrar[Step] {
  override val modID: String = Libstellar.MOD_ID
  override val registry: Registry[CodecType[Step]] = makeReg("step")

  val sleep: CodecType[Step] = register("sleep", SleepStep.codec)
  val multi: CodecType[Step] = register("multi", MultiStep.codec)
  val conditional: CodecType[Step] = register("conditional", ConditionalStep.codec)
  val clearCandle: CodecType[Step] = register("clear_candle", ClearCandleStep.codec)
  val consumeItem: CodecType[Step] = register("consume_item", ConsumeItemStep.codec)
  val dropItem: CodecType[Step] = register("drop_item", DropItemStep.codec)
  val summonEntity: CodecType[Step] = register("summon_entity", SummonEntityStep.codec)
  val playSound: CodecType[Step] = register("play_sound", PlaySoundStep.codec)
  val spawnParticles: CodecType[Step] = register("spawn_particles", SpawnParticlesStep.codec)
}
