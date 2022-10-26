package com.annas.githubuser.core.di

import androidx.room.Room
import com.annas.githubuser.core.BuildConfig
import com.annas.githubuser.core.data.UserRepository
import com.annas.githubuser.core.data.source.local.LocalDataSource
import com.annas.githubuser.core.data.source.local.room.FavoriteDatabase
import com.annas.githubuser.core.data.source.remote.RemoteDataSource
import com.annas.githubuser.core.data.source.remote.network.ApiService
import com.annas.githubuser.core.domain.repository.IUserRepository
import com.annas.githubuser.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FavoriteDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add("api.github.com", BuildConfig.CERT1)
            .add("api.github.com", BuildConfig.CERT2)
            .add("api.github.com", BuildConfig.CERT3)
            .build()

        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.TOKEN)
                .build()
            chain.proceed(newRequest)
        }).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API)
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