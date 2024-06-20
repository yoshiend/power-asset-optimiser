package no.yoshiend.power.asset.optimiser.domain

import no.yoshiend.power.asset.optimiser.infrastructure.persistence.Asset
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class OptimiseAsset {
    fun optimise(asset: Asset, getPowerPriceResponse: List<PowerPrice> ): List<Int> {
        val dailyBudget = asset.dailyPowerUsage - asset.minimumPowerUsage * 24
        val pricesAscending = getPowerPriceResponse.sortedBy { it.price }
        var remainingBudget = dailyBudget
        val maxHourlyAllocatable = asset.maximumPowerUsage - asset.minimumPowerUsage
        val powerUsage = (1 .. 24).associateWith { asset.minimumPowerUsage }.toMutableMap()
        pricesAscending.forEach { price ->
            if (remainingBudget > 0) {
                val allocate = min(remainingBudget, maxHourlyAllocatable)
                powerUsage[price.hour] = asset.minimumPowerUsage + allocate
                remainingBudget -= allocate
            }
        }
        return powerUsage.toSortedMap().values.toList()
    }
}