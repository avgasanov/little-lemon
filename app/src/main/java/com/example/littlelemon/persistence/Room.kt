package com.example.littlelemon.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemRecord(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val category: String,
)

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemRecord")
    fun getAll(): LiveData<List<MenuItemRecord>>

    @Insert
    fun insertAll(vararg menuItems: MenuItemRecord)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemRecord) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItemRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}