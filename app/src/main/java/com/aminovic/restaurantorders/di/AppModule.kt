package com.aminovic.restaurantorders.di

import android.app.Application
import androidx.room.Room
import com.aminovic.restaurantorders.data.local.OrderDatabase
import com.aminovic.restaurantorders.data.repository.OrderRepositoryImpl
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import com.aminovic.restaurantorders.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOrderDatabase(app: Application): OrderDatabase {
        return Room.databaseBuilder(
            app,
            OrderDatabase::class.java,
            "orders_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(db: OrderDatabase): OrderRepository {
        return OrderRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providesAddOrderUseCase(repository: OrderRepository): AddOrderUseCase {
        return AddOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetOrdersUseCase(repository: OrderRepository): GetOrdersUseCase {
        return GetOrdersUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesRemoveOrderUseCase(repository: OrderRepository): RemoveOrderUseCase {
        return RemoveOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetOrderUseCaseUseCase(repository: OrderRepository): GetOrderUseCase {
        return GetOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesChangeOrderStatusUseCase(
        repository: OrderRepository,
        removeOrderUseCase: RemoveOrderUseCase
    ): ChangeOrderStatusUseCase {
        return ChangeOrderStatusUseCase(repository, removeOrderUseCase)
    }


}