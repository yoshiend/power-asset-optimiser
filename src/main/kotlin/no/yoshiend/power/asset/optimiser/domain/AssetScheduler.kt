package no.yoshiend.power.asset.optimiser.domain

import no.yoshiend.power.asset.optimiser.infrastructure.persistence.AssetRepository
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PowerPriceClient
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PriceArea
import org.springframework.stereotype.Service

@Service
class AssetScheduler(
    private val assetRepository: AssetRepository,
    private val powerPriceClient: PowerPriceClient,
    private val optimiseAsset: OptimiseAsset
) {

    fun scheduleAssets(): List<AssetPowerPlan> {
        val assets = assetRepository.findAll().toList()
        val prices = powerPriceClient.getPrices("2024", "06", "20", PriceArea.NO1)
            .map { PowerPrice(it.timeStart.hour, it.pricePerKwhNok) }

        return assets.map { asset -> AssetPowerPlan(asset, optimiseAsset.optimise(asset, prices)) }
    }
}
