package ua.nure.smartlight.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.nure.smartlight.db.dao.GroupDao
import ua.nure.smartlight.db.dao.LampDao
import ua.nure.smartlight.db.entity.GroupEntity
import ua.nure.smartlight.db.entity.LampEntity

@Database(
    entities = [
        LampEntity::class,
        GroupEntity::class
    ],
    version = 3
)

@TypeConverters(DbConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract val lampDao: LampDao
    abstract val groupDao: GroupDao
}