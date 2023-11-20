package com.example.littlelemon.persistence

import android.content.Context
import androidx.room.Room
import com.example.littlelemon.persistence.MenuRepository.Companion.lock
import com.example.littlelemon.persistence.model.MenuItemNetwork
import com.example.littlelemon.persistence.model.MenuNetworkdata
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.sync.Mutex

class MenuRepository(context: Context) {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Companion.database = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
        Companion.database!!
    }

    suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetworkdata>().menu
    }

    suspend fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }

    fun getMenuItems() = database.menuItemDao().getAll()

    suspend fun isRepoEmpty() = database.menuItemDao().isEmpty()

    companion object {
        val lock = Mutex(false)
        private var database: AppDatabase? = null
            set(db)  {
                synchronized(AppDatabase::class) {
                    if(field == null) {
                        field = db
                    }
                }
            }
    }
}