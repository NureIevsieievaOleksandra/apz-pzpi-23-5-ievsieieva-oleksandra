package ua.nure.smartlightadmin.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import ua.nure.smartlightadmin.ui.auth.signin.SignInViewModel
import ua.nure.smartlightadmin.ui.dashboard.DashboardViewModel

val appModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::DashboardViewModel)
}