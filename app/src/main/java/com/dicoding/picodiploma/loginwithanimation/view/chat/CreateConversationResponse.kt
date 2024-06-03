package com.dicoding.picodiploma.loginwithanimation.view.chat

import com.dicoding.picodiploma.loginwithanimation.data.response.ConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.LatestMessage

fun CreateConversationResponse.toConversationResponse(): ConversationResponse {
    return ConversationResponse(
        id = this.id,
        name = this.name,
        picture = this.picture,
        isGroup = this.isGroup,
        users = this.users,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        version = this.version,
        latestMessage = this.latestMessage?.let {
            LatestMessage(
                id = it.id,
                sender = it.sender,
                message = it.message,
                conversation = it.conversation,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                version = it.version
            )
        }
    )
}