package com.ssafy.foodfind.di

import com.ssafy.foodfind.data.repository.user.UserRepository
import com.ssafy.foodfind.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsUserRepository(
        repositoryImpl: UserRepositoryImpl
    ): UserRepository
}