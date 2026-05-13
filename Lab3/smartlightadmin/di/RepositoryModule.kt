package ua.nure.smartlightadmin.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.nure.smartlight.repository.token.TokenRepositoryImpl
import ua.nure.smartlightadmin.repository.admin.AdminRepository
import ua.nure.smartlightadmin.repository.admin.AdminRepositoryImpl
import ua.nure.smartlightadmin.repository.auth.AuthRepository
import ua.nure.smartlightadmin.repository.auth.AuthRepositoryImpl
import ua.nure.smartlightadmin.repository.group.GroupRepository
import ua.nure.smartlightadmin.repository.group.GroupRepositoryImpl
import ua.nure.smartlightadmin.repository.token.TokenRepository
import ua.nure.smartlightadmin.repository.user.UserRepository
import ua.nure.smartlightadmin.repository.user.UserRepositoryImpl

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::TokenRepositoryImpl).bind<TokenRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::GroupRepositoryImpl).bind<GroupRepository>()
    singleOf(::AdminRepositoryImpl).bind<AdminRepository>()
}