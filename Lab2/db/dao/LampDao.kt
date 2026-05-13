package ua.nure.smartlight.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import ua.nure.smartlight.db.entity.LampEntity

@Dao
interface LampDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(list: List<LampEntity>)

    @Query("DELETE FROM LampEntity WHERE lampId=:lampId")
    suspend fun delete(lampId: Long)
}