package com.edy.oompaloompas.di

import android.content.Context
import androidx.room.Room
import com.edy.oompaloompas.data.datasources.OompaLoompasLocalDataSource
import com.edy.oompaloompas.data.datasources.OompaLoompasRemoteDataSource
import com.edy.oompaloompas.data.local.roomsql.OompaLoompaLocalDataSourceImpl
import com.edy.oompaloompas.data.local.roomsql.OompaLoompasDataBase
import com.edy.oompaloompas.data.remote.retrofitdatasource.services.OompaLoompasRemoteDataSourceImpl
import com.edy.oompaloompas.data.repositories.OompaLoompaRepositoryImpl
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OompaLoompasModule {

    @Provides
    fun provideOompaLoompasRemoteDataSource(remoteDataSource: OompaLoompasRemoteDataSourceImpl): OompaLoompasRemoteDataSource = remoteDataSource

    @Provides
    fun provideOompaLoompasLocalDataSource(localDataSource: OompaLoompaLocalDataSourceImpl): OompaLoompasLocalDataSource = localDataSource

    @Singleton
    @Provides
    fun provideOompaLoompasLocalDataBase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        OompaLoompasDataBase::class.java,
        "db_oompa_loompas"
    ).build()

    @Singleton
    @Provides
    fun provideOompaLoompasDao(db: OompaLoompasDataBase) = db.oompaLoompasDao()

    @Provides
    fun provideOompaLoompasRepository(repository: OompaLoompaRepositoryImpl): OompaLoompaRepository = repository
}