package stellarwitch7.libstellar.registry.codec

import com.mojang.serialization.Codec
import com.mojang.serialization.Lifecycle
import com.mojang.serialization.MapCodec
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import stellarwitch7.libstellar.registry.Registrar

trait CodecRegistrar[T <: CodecTypeProvider[T]] extends Registrar[CodecType[T]] {
  def codec: Codec[T] = registry.getCodec().dispatchMap[T](_.`type`, _.codec).codec()
  def makeReg(name: String): Registry[CodecType[T]] = SimpleRegistry(RegistryKey.ofRegistry(id(name)), Lifecycle.stable())
  def register[V <: T](name: String, codec: MapCodec[? <: V]): CodecType[T] = register(name, CodecType(codec))
}
