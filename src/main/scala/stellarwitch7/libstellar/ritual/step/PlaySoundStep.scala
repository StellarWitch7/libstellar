package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import scala.collection.mutable.ArrayDeque

/**
 * Plays the given sound at the given offset from the centre of the ritual with the given volume and pitch.
 * @param sound the sound to play.
 * @param offset the offset from the ritual centre to play the sound.
 * @param volume the volume at which to play the sound.
 * @param pitch the pitch at which to play the sound.
 */
class PlaySoundStep(val sound: SoundEvent, val offset: BlockPos, val volume: Float, val pitch: Float) extends Step {
  override val `type`: CodecType[Step] = Step.playSound

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    world.playSoundAtBlockCenter(ritual.pos.add(offset), sound, SoundCategory.BLOCKS, volume, pitch, true)
    true
  }
}

object PlaySoundStep {
  val codec: MapCodec[PlaySoundStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
    Registries.SOUND_EVENT.getCodec().fieldOf("sound").forGetter(_.sound),
    BlockPos.CODEC.fieldOf("offset").forGetter(_.offset),
    Codec.FLOAT.fieldOf("volume").forGetter(_.volume),
    Codec.FLOAT.fieldOf("pitch").forGetter(_.pitch)
  ).apply(builder, PlaySoundStep(_, _, _, _)))
}
