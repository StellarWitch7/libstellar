package stellarwitch7.libstellar.ritual.step

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import stellarwitch7.libstellar.registry.codec.CodecType
import stellarwitch7.libstellar.ritual.Ritual
import stellarwitch7.libstellar.ritual.step.Step.Companion.summonEntity

/**
 * Spawns the given entity at the given offset from the ritual centre.
 * Always succeeds, even if entity spawning fails due to internal error.
 * @param entityType the type of entity to spawn.
 * @param offset the offset from the ritual centre to spawn the entity.
 */
class SummonEntityStep(val entityType: EntityType<*>, val offset: BlockPos) : Step {
    override val type: CodecType<Step> = summonEntity

    override fun apply(world: ServerWorld, ritual: Ritual, steps: ArrayDeque<Step>): Boolean {
        val entity = entityType.create(world)
        val offsetPos = ritual.pos.add(offset)

        entity?.let {
            it.setPosition(offsetPos.toCenterPos())
            world.spawnEntity(it)
        }

        return true
    }

    companion object {
        val codec: MapCodec<SummonEntityStep> = RecordCodecBuilder.mapCodec { builder ->
            builder.group(
                Registries.ENTITY_TYPE.codec.fieldOf("entity_type").forGetter(SummonEntityStep::entityType),
                BlockPos.CODEC.fieldOf("offset").forGetter(SummonEntityStep::offset)
            ).apply(builder, ::SummonEntityStep)
        }
    }
}