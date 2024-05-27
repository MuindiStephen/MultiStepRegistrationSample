package com.example.multistepregistrationsample.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.multistepregistrationsample.data.api.ApiService
import com.example.multistepregistrationsample.data.api.ApiService.Companion.BASE_URL
import com.example.multistepregistrationsample.data.repo.FarmerRepository
import com.example.multistepregistrationsample.data.room.AppDatabase
import com.example.multistepregistrationsample.data.room.FarmerRegistrationDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Application) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "farmer-test.db")
            .allowMainThreadQueries()  // without blocking the main thread
            .fallbackToDestructiveMigration() //  Want database to not be cleared when upgrading versions from 1_2
            // .addMigrations()
            .build()
    }

    @Singleton
    @Provides
    fun providesFarmerRegistrationDao(appDatabase: AppDatabase): FarmerRegistrationDao {
        return appDatabase.farmerRegistrationDao()
    }

    @Provides
    @Singleton
    fun provideFarmerRepository(
        farmerDao: FarmerRegistrationDao,
        apiService: ApiService
    ): FarmerRepository {
        return FarmerRepository(farmerDao,apiService)
    }

    @Singleton
    @Provides
    fun providesDispatcher() = Dispatchers.Main as CoroutineDispatcher


    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }



    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

}