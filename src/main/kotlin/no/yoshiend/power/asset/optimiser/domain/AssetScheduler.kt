package no.yoshiend.power.asset.optimiser.domain

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.AssetPowerPlan
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.AssetRepository
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PowerPriceClient
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PriceArea
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AssetScheduler(
    private val assetRepository: AssetRepository,
    private val powerPriceClient: PowerPriceClient,
    private val optimiseAsset: OptimiseAsset
) {

    companion object {
        const val HOURLY = "0 0 * * * *"
        const val EVERY_MINUTE = "0 * * * * *"
    }

    @Scheduled(cron = EVERY_MINUTE)
    @SchedulerLock(name = "AssetScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT10M")
    fun scheduleAssets() {
        val planForDate = LocalDate.now().plusDays(1)
        val prices = powerPriceClient.getPrices(
            planForDate.year.toString(),
            String.format("%02d", planForDate.monthValue),
            String.format("%02d", planForDate.dayOfMonth),
            PriceArea.NO1
        ).map { PowerPrice(it.timeStart.hour, it.pricePerKwhNok) }

        val assets = assetRepository.findAll()
            .map { asset ->
                asset.copy(
                    powerPlan = AssetPowerPlan(
                        planForDate,
                        optimiseAsset.optimise(asset, prices)
                    )
                )
            }

        assetRepository.saveAll(assets)
    }
}
