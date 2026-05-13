package ua.nure.smartlight.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow
import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.db.entity.GroupEntity
import ua.nure.smartlight.db.entity.LampEntity

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<GroupEntity>)
    @Query("SELECT * FROM GROUPENTITY")
    fun get(): Flow<List<Group>>

    @Query("DELETE FROM GroupEntity WHERE groupId=:groupId")
    suspend fun deleteById(groupId: Long)
}