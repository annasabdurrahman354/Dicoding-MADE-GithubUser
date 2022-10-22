package com.annas.githubuser.core.di

import androidx.room.Room
import com.annas.githubuser.core.data.UserRepository
import com.annas.githubuser.core.data.source.local.LocalDataSource
import com.annas.githubuser.core.data.source.local.room.FavoriteDatabase
import com.annas.githubuser.core.data.source.remote.RemoteDataSource
import com.annas.githubuser.core.data.source.remote.network.ApiService
import com.annas.githubuser.core.domain.repository.IUserRepository
import com.annas.githubuser.core.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FavoriteDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "token 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")
                .build()
            chain.proceed(newRequest)
        }).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IUserRepository> {
        UserRepository(
            get(),
            get(),
            get()
        )
    }
}