package stellarwitch7.libstellar.utils

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec

object KCodecUtils {
    val uIntCodec: Codec<UInt> = Codec.INT.xmap(Int::toUInt, UInt::toInt)

    fun <T> queueCodec(inner: Codec<T>): Codec<ArrayDeque<T>> {
        return inner.listOf().xmap(::ArrayDeque, ArrayDeque<T>::toList)
    }

    fun <T> lazyMapCodec(function: () -> MapCodec<T>): MapCodec<T> {
        return MapCodec.recursive(function.toString(), { function() })
    }
}