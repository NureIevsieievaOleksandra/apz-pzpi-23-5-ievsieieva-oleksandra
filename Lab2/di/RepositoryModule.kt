package ua.nure.smartlight.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ua.nure.smartlight.repository.db.DbRepository
import ua.nure.nomnomsave.db.DbRepositoryImpl
import ua.nure.smartlight.repository.analytics.AnalyticsRepository
import ua.nure.smartlight.repository.analytics.AnalyticsRepositoryImpl
import ua.nure.smartlight.repository.token.TokenRepository
import ua.nure.smartlight.repository.token.TokenRepositoryImpl
import ua.nure.smartlight.repository.auth.AuthRepository
import ua.nure.smartlight.repository.auth.AuthRepositoryImpl
import ua.nure.smartlight.repository.group.GroupRepository
import ua.nure.smartlight.repository.group.GroupRepositoryImpl
import ua.nure.smartlight.repository.lamp.LampRepository
import ua.nure.smartlight.repository.lamp.LampRepositoryImpl
import javax.inject.Singleton


@OptIn(ExperimentalCoroutinesApi::class)
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDbRepository(
        @ApplicationContext context: Context,
        tokenRepository: TokenRepository
    ): DbRepository = DbRepositoryImpl(
        context = context,
        tokenRepository = tokenRepository
    )

    @Provides
    @Singleton
    fun provideTokenRepository(
        dataStore: DataStore<Preferences>
    ): TokenRepository = TokenRepositoryImpl(
        dataStore = dataStore
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideAuthRepository(
        httpClient: HttpClient,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
        dbRepository: DbRepository,
        tokenRepository: TokenRepository,
    ): AuthRepository = AuthRepositoryImpl(
        httpClient = httpClient,
        dbDeliveryDispatcher = dbDeliveryDispatcher,
        dbRepository = dbRepository,
        tokenRepository = tokenRepository
    )

    @Provides
    fun provideGroupRepository(
        httpClient: HttpClient,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
        dbRepository: DbRepository,
    ): GroupRepository = GroupRepositoryImpl(
        httpClient = httpClient,
        dbRepository = dbRepository,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )

    @Provides
    fun provideLampRepository(
        httpClient: HttpClient,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
        dbRepository: DbRepository,
    ): LampRepository = LampRepositoryImpl(
        httpClient = httpClient,
        dbRepository = dbRepository,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )

    @Provides
    fun provideAnalyticsRepository(
        httpClient: HttpClient,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
        dbRepository: DbRepository,
    ): AnalyticsRepository = AnalyticsRepositoryImpl(
        httpClient = httpClient,
        dbRepository = dbRepository,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )
}