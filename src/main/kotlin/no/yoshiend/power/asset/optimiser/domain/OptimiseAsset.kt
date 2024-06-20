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

        var budget = asset.dailyPowerUsage - asset.minimumPowerUsage * 24
        val powerUsage = mutableMapOf<Int, PowerUsage>()
        pricesAscending.forEach { price ->
            val allocate = min(budget, maxHourlyAllocatable)
            powerUsage[price.hour] = PowerUsage(null, price.hour, asset.minimumPowerUsage + allocate)
            budget -= allocate
        }
        println("Remaining budget: $budget")
        println("Allocated power: ${powerUsage.values.sumOf { it.usage }}")
        println("Asset daily power usage: ${asset.dailyPowerUsage}")
        println("Asset minimum power usage: ${asset.minimumPowerUsage}")
        println("Asset maximum power usage: ${asset.maximumPowerUsage}")
        println("Asset power usage: ${asset.powerPlan.powerUsage.map { it.usage }}")
        return powerUsage.toSortedMap().values.toList()
    }
}