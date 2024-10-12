package stellarwitch7.libstellar.registry.codec

import com.mojang.serialization.MapCodec

class CodecType[T](val codec: MapCodec[? <: T])
