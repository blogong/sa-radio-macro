package net.bk24.macro.worker.infrastructure.discord

import discord4j.common.util.Snowflake
import discord4j.core.DiscordClientBuilder

object Bot {
    fun send(msg: String) {
        val token = "MTIyODk0NjY3MjgwNjAwMjc0OA.GgikzI.xYxIC-6pX2ti8DIMMpclq3LqEKu-kZsOsSMofI" // 발급받은 토큰을 여기에 입력하세요.
        val channelId = "1228945931081355315"

        val client = DiscordClientBuilder.create(token).build()

        client.getChannelById(Snowflake.of(channelId))
            .createMessage(msg)
            .subscribe()
    }
}
