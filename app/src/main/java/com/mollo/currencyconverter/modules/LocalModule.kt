package com.mollo.currencyconverter.modules

import android.content.Context
import androidx.room.Room
import com.mollo.currencyconverter.database.CurrencyDB
import com.mollo.currencyconverter.database.RateDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesLocalStore(@ApplicationContext context: Context) : CurrencyDB {
        return Room.databaseBuilder(
            context,
            CurrencyDB::class.java,
            "currencyDB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFarmDao(myDB: CurrencyDB) : RateDAO = myDB.coverterDao()

}