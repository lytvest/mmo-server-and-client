package ru.lytvest.learn


import com.google.gson.*
import ru.lytvest.aeon.Player
import java.lang.reflect.Type


class PlayerGsonAdapter : JsonSerializer<Player>, JsonDeserializer<Player> {
    override fun serialize(player: Player, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.add("type", JsonPrimitive(player.javaClass.canonicalName))
        result.add("info", context.serialize(player, player.javaClass))

        return result
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): Player {
        val jsonObject = json.asJsonObject
        val type = jsonObject["type"].asString
        val element = jsonObject["info"]

        return try {
            context.deserialize(element, Class.forName(type))
        } catch (exception: ClassNotFoundException) {
            throw JsonParseException("Unknown element type: $type", exception)
        }
    }

}