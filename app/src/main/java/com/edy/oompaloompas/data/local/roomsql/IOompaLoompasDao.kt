package com.edy.oompaloompas.data.local.roomsql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface IOompaLoompasDao {
    @Query("SELECT * FROM oompa_loompas")
    fun getAll(): Flow<List<OompaLoompaLocalDTO>>

    @Query("SELECT * FROM oompa_loompas WHERE is_favorite=1 LIMIT 8")
    fun getAllFavoritesWithLimit(): Flow<List<OompaLoompaLocalDTO>>

    @Query("SELECT * FROM oompa_loompas WHERE is_favorite=1")
    fun getAllFavorites(): Flow<List<OompaLoompaLocalDTO>>

    @Query("SELECT DISTINCT profession FROM oompa_loompas")
    fun getAllProfessions(): List<String>

    @Query("SELECT DISTINCT gender FROM oompa_loompas")
    fun getAllGenders(): List<String>

    @Query("SELECT COUNT(id) FROM oompa_loompas")
    suspend fun oompaLoompasCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOompaLoompas(oompaLoompas: List<OompaLoompaLocalDTO>)

    @Query("UPDATE oompa_loompas SET is_favorite=1 WHERE id = :id")
    fun postFavorite(id: Int): Int

    @Query("UPDATE oompa_loompas SET is_favorite=0 WHERE id = :id")
    fun postUnfavorite(id: Int): Int

    @RawQuery
    fun getOompaLoompaWithFilter(query: SupportSQLiteQuery): List<OompaLoompaLocalDTO>
}