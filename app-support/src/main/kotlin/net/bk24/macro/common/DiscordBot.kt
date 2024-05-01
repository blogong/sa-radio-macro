package net.bk24.macro.common

import discord4j.common.util.Snowflake
import discord4j.core.DiscordClient

object DiscordBot {
    var client: DiscordClient = DiscordClient.create("MTIyODk0NjY3MjgwNjAwMjc0OA.GQEvD0.vrKMMI9qm8EakuspSk9aSOhDlkllkbG3ARkfhE")

    fun sendMessage(message: String) {
        client.getChannelById(Snowflake.of("1228945931081355315"))
            .createMessage(message)
            .subscribe()
    }
}
