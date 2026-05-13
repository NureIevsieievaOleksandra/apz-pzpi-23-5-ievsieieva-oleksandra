package ua.nure.smartlight.repository.db

import kotlinx.coroutines.flow.Flow
import ua.nure.smartlight.db.AppDb

interface DbRepository {
    val dbFlow: Flow<AppDb>
    val db: AppDb
}