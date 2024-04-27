package net.bk24.macro.worker.application

import net.bk24.macro.common.ServerTodo

interface CreateComment {
    fun execute(serverTodo: ServerTodo)
}
