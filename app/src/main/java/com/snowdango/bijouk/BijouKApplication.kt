package com.snowdango.bijouk

import android.app.Application
import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog
import com.snowdango.bijouk.analytics.AnalyticsModule
import com.snowdango.bijouk.analytics.LogUtil
import com.snowdango.bijouk.domain.DomainModule
import com.snowdango.bijouk.infla.InflaModule
import com.snowdango.bijouk.model.ModelModule
import com.snowdango.bijouk.model.settings.DebugSettingsModel
import com.snowdango.bijouk.presenter.featureModules
import com.snowdango.bijouk.presenter.presenterModule
import com.snowdango.bijouk.repository.RepositoryModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BijouKApplication : Application(), KoinComponent {

    private val mainJob = Job()

    override fun onCreate() {
        super.onCreate()
        GlobalContext.getOrNull() ?: startKoin {
            androidContext(this@BijouKApplication)
            modules(
                applicationModule,
                AnalyticsModule.Module,
                RepositoryModule.module,
                ModelModule.module,
                DomainModule.actualModule,
                InflaModule.module,
                presenterModule
            )
            modules(featureModules(BuildConfig.VERSION_NAME, BuildConfig.DEBUG))
        }
        logSettings()
        // socket connect log
        initializeMarsXLog(
            context = applicationContext,
            logDir = "",
            level = Logging.LEVEL_INFO,
            namePrefix = ""
        )
    }

    private fun logSettings() {
        CoroutineScope(Dispatchers.IO + mainJob).launch {
            val debugSettingsModel by inject<DebugSettingsModel>()
            debugSettingsModel.getFlowData().collect {
                LogUtil.isWriteSocketEventLog = it.isWriteSocketEventLog
            }
        }
    }

    val applicationModule = module {
        single { CoroutineScope(Dispatchers.IO + mainJob) }
    }

    override fun onTerminate() {
        super.onTerminate()
        mainJob.cancel()
    }
}
