package com.uruklabs.newsspace

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.uruklabs.newsspace.data.dao.PostDao
import com.uruklabs.newsspace.data.repository.PostRepository
import com.uruklabs.newsspace.data.repository.PostRepositoryImpl
import com.uruklabs.newsspace.data.services.SpaceFightNewsServices
import com.uruklabs.newsspace.domain.GetLatestPostsByTitleUseCase
import com.uruklabs.newsspace.domain.GetLatestPostsUseCase
import io.mockk.mockk
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModuleForTest() = module {

    factory<GetLatestPostsUseCase> { GetLatestPostsUseCase(get()) }
    factory<GetLatestPostsByTitleUseCase> { GetLatestPostsByTitleUseCase(get()) }

}

fun configureDataModuleForTest(baseUrl: String) = module {

    single {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFightNewsServices::class.java)
    }
    single<PostRepository> {
        PostRepositoryImpl(get(), mockk<PostDao>())
    }

}