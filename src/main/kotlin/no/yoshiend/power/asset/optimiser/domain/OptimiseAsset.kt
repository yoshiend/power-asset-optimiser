package no.yoshiend.power.asset.optimiser.domain

import no.yoshiend.power.asset.optimiser.infrastructure.persistence.Asset
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.PowerUsage
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class OptimiseAsset {
    fun optimise(asset: Asset, getPowerPriceResponse: List<PowerPrice> ): List<PowerUsage> {
        val pricesAscending = getPowerPriceResponse.sortedBy { it.price }
        val maxHourlyAllocatable = asset.maximumPowerUsage - asset.minimumPowerUsage
        var allocatable = asset.dailyPowerUsage - asset.minimumPowerUsage * 24
        val powerUsage = mutableMapOf<Int, PowerUsage>()
        for (price in pricesAscending) {
            val allocate = min(allocatable, maxHourlyAllocatable)
            powerUsage[price.hour] = PowerUsage(null, price.hour, asset.minimumPowerUsage + allocate)
            allocatable -= allocate
        }
        return powerUsage.toSortedMap().values.toList()
    }
}