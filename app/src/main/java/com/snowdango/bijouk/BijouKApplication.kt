package com.snowdango.bijouk

import android.app.Application
import com.snowdango.bijouk.domain.DomainModule
import com.snowdango.bijouk.model.ModelModule
import com.snowdango.bijouk.presenter.PresenterModule
import com.snowdango.bijouk.repository.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class BijouKApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.getOrNull() ?: startKoin {
            androidContext(this@BijouKApplication)
            modules(
                RepositoryModule.module,
                ModelModule.module,
                DomainModule.module,
                PresenterModule.modules,
                PresenterModule.queueModule,
                PresenterModule.searchModule,
                PresenterModule.artistModule,
                PresenterModule.deviceModule,
                PresenterModule.settingModule(BuildConfig.VERSION_NAME),
            )
        }
    }
}
