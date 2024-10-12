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
import scala.collection.mutable.ArrayDeque
import stellarwitch7.libstellar.util.StellarCodecUtils
import com.mojang.serialization.Codec

/**
 * Spawns particles at an offset from the ritual centre.
 * @param particle the particle effect to spawn.
 * @param offset the double-precision offset from the ritual centre.
 * @param delta the x,y,z distances that particles may be spawned from the offset position.
 * @param count the amount of particles to spawn.
 * @param speed used to determine particle speed if `count` is zero. In such a scenario, `delta` serves as a movement direction instead.
 */
class SpawnParticlesStep(val particle: ParticleEffect, val offset: Vec3d, val delta: Vec3d, val count: Int = 1, val speed: Double = 0.0) extends Step {
  override val `type`: CodecType[Step] = Step.spawnParticles

  override def apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque[Step]): Boolean = {
    val offsetPos = ritual.pos.toCenterPos().add(offset)
    world.spawnParticles(particle,
      offsetPos.getX(), offsetPos.getY(), offsetPos.getX(),
      count, delta.x, delta.y, delta.z, speed)
    
    true
  }
}

object SpawnParticlesStep {
  val codec: MapCodec[SpawnParticlesStep] = RecordCodecBuilder.mapCodec(builder => builder.group(
    Registries.PARTICLE_TYPE.getCodec().dispatchMap((p: ParticleEffect) => p.getType, _.getCodec).codec().fieldOf("particle").forGetter(_.particle),
    Vec3d.CODEC.fieldOf("offset").forGetter(_.offset),
    Vec3d.CODEC.fieldOf("delta").forGetter(_.delta),
    Codec.INT.fieldOf("count").forGetter(_.count)
  ).apply(builder, SpawnParticlesStep(_, _, _, _)))
}
