package com.example.grpc_chat_android.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.grpc_chat_android.db.MessageDatabase
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.db.entities.ChatPreview
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatDaoTest {
    private lateinit var chatDao: ChatDao
    private lateinit var database: MessageDatabase

    @Before
    fun initdb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MessageDatabase::class.java).build()
        chatDao = database.chatDao()
    }

    @After
    fun closeConn() {
        database.close()
    }

    @Test
    fun testChatDaoInsert() {
        runBlocking { chatDao.insertChat(ChatEntity(1, "message", "string", "", "1", 1)) }
        val cursor = database.query("Select * From ChatEntity", arrayOf())
        cursor.moveToFirst()

        Truth.assertThat(cursor.count).isEqualTo(1)

        Truth.assertThat(cursor.getLong(cursor.getColumnIndexOrThrow("id"))).isEqualTo(1)
        Truth.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("body")))
            .isEqualTo("message")
        Truth.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("from")))
            .isEqualTo("string")
        Truth.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("to")))
            .isEqualTo("")
        Truth.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("chatId"))).isEqualTo("1")
        Truth.assertThat(cursor.getLong(cursor.getColumnIndexOrThrow("time"))).isEqualTo(1)
    }

    @Test
    fun testDaoFindOne() {
        runBlocking {
            chatDao.insertChat(ChatEntity(1, "message", "sender1", "", "1", 1))
            chatDao.insertChat(ChatEntity(2, "message", "sender2", "", "2", 1))
            val message = chatDao.loadOneChat("2").first()
            Truth.assertThat(message).containsExactly(ChatEntity(2, "message", "sender2", "", "2", 1))
        }
    }

    @Test
    fun testDaoFindOne_NoResult() {
        runBlocking {
            chatDao.insertChat(ChatEntity(1, "message", "sender1", "", "1", 1))
            chatDao.insertChat(ChatEntity(2, "message", "sender2", "", "2", 1))
            val message = chatDao.loadOneChat("3").first()
            Truth.assertThat(message).isEmpty()
        }
    }

    @Test
    fun testDaoChatPreview() {
        runBlocking {
            chatDao.insertChat(ChatEntity(1, "message", "sender1", "user", "1", 1))
            chatDao.insertChat(ChatEntity(2, "message", "sender2", "user", "2", 1))
            val message = chatDao.loadChatPreview().first()
            Truth.assertThat(message)
                .containsExactly(ChatPreview("1"), ChatPreview("2"))
        }
    }
}
