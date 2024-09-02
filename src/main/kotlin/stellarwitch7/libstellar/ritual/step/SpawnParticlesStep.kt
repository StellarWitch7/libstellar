package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step.Companion.spawnParticles
import stellarwitch7.libstellar.utils.KCodecUtils

/**
 * Spawns particles at an offset from the ritual centre.
 * @param particle the particle effect to spawn.
 * @param offset the double-precision offset from the ritual centre.
 * @param delta the x,y,z distances that particles may be spawned from the offset position.
 * @param count the amount of particles to spawn.
 * @param speed used to determine particle speed if `count` is zero. In such a scenario, `delta` serves as a movement direction instead.
 */
class SpawnParticlesStep(val particle: ParticleEffect, val offset: Vec3d, val delta: Vec3d, val count: UInt = 1u, val speed: Double = 0.0) : Step {
    override val type: CodecType<Step> = spawnParticles

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        val offsetPos = ritual.pos.toCenterPos().add(offset)
        world.spawnParticles(particle,
            offsetPos.x, offsetPos.y, offsetPos.z,
            count.toInt(), delta.x, delta.y, delta.z, speed)
        return true
    }

    companion object {
        val codec: MapCodec<SpawnParticlesStep> = RecordCodecBuilder.mapCodec { builder ->
            builder.group(
                Registries.PARTICLE_TYPE.codec.dispatchMap(ParticleEffect::getType, ParticleType<*>::getCodec).codec().fieldOf("particle").forGetter(SpawnParticlesStep::particle),
                Vec3d.CODEC.fieldOf("offset").forGetter(SpawnParticlesStep::offset),
                Vec3d.CODEC.fieldOf("delta").forGetter(SpawnParticlesStep::delta),
                KCodecUtils.uIntCodec.fieldOf("count").forGetter(SpawnParticlesStep::count)
            ).apply(builder, ::SpawnParticlesStep)
        }
    }
}