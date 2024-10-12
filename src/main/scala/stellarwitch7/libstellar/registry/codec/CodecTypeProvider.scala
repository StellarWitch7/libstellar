package stellarwitch7.libstellar.registry.codec

trait CodecTypeProvider[T <: CodecTypeProvider[T]] {
  val `type`: CodecType[T]
}
