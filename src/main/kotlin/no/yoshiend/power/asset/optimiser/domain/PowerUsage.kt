package no.yoshiend.power.asset.optimiser.domain

import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PriceArea

data class PowerUsage(
    val assetId: Long,
    val hour: Int,
    val usage: Double,
    val priceArea: PriceArea
)
