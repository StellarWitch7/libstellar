package stellarwitch7.libstellar.util

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import scala.collection.mutable.ArrayDeque
import scala.collection.JavaConverters._

object StellarCodecUtils {
  def queueCodec[T](inner: Codec[T]): Codec[ArrayDeque[T]] = inner.listOf().xmap(value => (ArrayDeque().addAll(value.asScala)), _.toList.asJava)
  def lazyMapCodec[T](function: () => MapCodec[T]): MapCodec[T] = MapCodec.recursive(function.toString(), _ => function())
}

