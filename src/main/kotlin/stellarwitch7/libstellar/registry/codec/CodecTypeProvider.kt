package stellarwitch7.libstellar.registry.codec

interface CodecTypeProvider<T : CodecTypeProvider<T>> {
    val type: CodecType<T>
}