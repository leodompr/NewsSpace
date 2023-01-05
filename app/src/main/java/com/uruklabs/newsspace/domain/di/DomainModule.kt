package com.uruklabs.newsspace.domain.di

import com.uruklabs.newsspace.domain.GetLatestPostsUsecase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostsUsecase(get()) }
        }
    }

}