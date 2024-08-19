package stellarwitch7.libstellar.utils

import com.mojang.serialization.Codec

object KCodecUtils {
    val uIntCodec: Codec<UInt> = Codec.INT.xmap(Int::toUInt, UInt::toInt)
}