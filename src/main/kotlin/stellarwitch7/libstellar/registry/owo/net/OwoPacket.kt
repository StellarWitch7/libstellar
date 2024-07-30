package stellarwitch7.libstellar.registry.owo.net

interface OwoPacket<T> where T : OwoPacket<T>, T : Record {
}