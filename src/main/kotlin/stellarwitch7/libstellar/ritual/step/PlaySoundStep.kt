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
import stellarwitch7.libstellar.ritual.step.Step.Companion.playSound

/**
 * Plays the given sound at the given offset from the centre of the ritual with the given volume and pitch.
 * @param sound the sound to play.
 * @param offset the offset from the ritual centre to play the sound.
 * @param volume the volume at which to play the sound.
 * @param pitch the pitch at which to play the sound.
 */
class PlaySoundStep(val sound: SoundEvent, val offset: BlockPos, val volume: Float, val pitch: Float) : Step {
    override val type: CodecType<Step> = playSound

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        world.playSoundAtBlockCenter(ritual.pos.add(offset), sound, SoundCategory.BLOCKS, volume, pitch, true)
        return true
    }

    companion object {
        val codec: MapCodec<PlaySoundStep> = RecordCodecBuilder.mapCodec { builder ->
            builder.group(
                Registries.SOUND_EVENT.codec.fieldOf("sound").forGetter(PlaySoundStep::sound),
                BlockPos.CODEC.fieldOf("offset").forGetter(PlaySoundStep::offset),
                Codec.FLOAT.fieldOf("volume").forGetter(PlaySoundStep::volume),
                Codec.FLOAT.fieldOf("pitch").forGetter(PlaySoundStep::pitch)
            ).apply(builder, ::PlaySoundStep)
        }
    }
}