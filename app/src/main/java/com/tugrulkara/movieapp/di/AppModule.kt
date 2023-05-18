package com.tugrulkara.movieapp.di

import android.content.Context
import androidx.room.Room
import com.tugrulkara.movieapp.data.repository.LocalRepository
import com.tugrulkara.movieapp.data.repository.LocalRepositoryImpl
import com.tugrulkara.movieapp.data.repository.RemoteRepository
import com.tugrulkara.movieapp.data.repository.RemoteRepositoryImpl
import com.tugrulkara.movieapp.data.roomdb.MoviesDAO
import com.tugrulkara.movieapp.data.roomdb.MoviesDatabase
import com.tugrulkara.movieapp.data.service.MainService
import com.tugrulkara.movieapp.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitService() :  MainService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainService::class.java)
    }

    @Singleton
    @Provides
    fun injectRoomDB(@ApplicationContext context: Context)= Room.databaseBuilder(context,
        MoviesDatabase::class.java,"moviesdatabase").build()

    @Singleton
    @Provides
    fun injectDAO(database: MoviesDatabase)=database.dao()

    @Singleton
    @Provides
    fun injectRemoteRepository(mainService: MainService) = RemoteRepositoryImpl(mainService) as RemoteRepository

    @Singleton
    @Provides
    fun injectLocalRepository(moviesDAO: MoviesDAO)= LocalRepositoryImpl(moviesDAO) as LocalRepository

}