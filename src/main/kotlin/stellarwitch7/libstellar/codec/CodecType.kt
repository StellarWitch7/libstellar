package stellarwitch7.libstellar.codec

import com.mojang.serialization.MapCodec

data class CodecType<T>(val codec: MapCodec<out T>)
