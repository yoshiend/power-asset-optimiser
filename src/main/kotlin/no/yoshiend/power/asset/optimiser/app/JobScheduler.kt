package no.yoshiend.power.asset.optimiser.app

import org.springframework.scheduling.annotation.Scheduled
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import no.yoshiend.power.asset.optimiser.domain.AssetScheduler
import org.springframework.stereotype.Component

@Component
class JobScheduler(
    private val assetScheduler: AssetScheduler
) {
    companion object {
        const val HOURLY = "0 0 * * * *"
        const val EVERY_MINUTE = "0 * * * * *"
    }

    @Scheduled(cron = EVERY_MINUTE)
    @SchedulerLock(name = "AssetScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT10M")
    fun scheduleAssets() {
        assetScheduler.scheduleAssets()
    }
}