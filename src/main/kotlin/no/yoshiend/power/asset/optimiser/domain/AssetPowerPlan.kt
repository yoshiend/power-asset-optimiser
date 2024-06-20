package no.yoshiend.power.asset.optimiser.domain

import no.yoshiend.power.asset.optimiser.infrastructure.persistence.Asset

class AssetPowerPlan(
    val asset: Asset,
    val powerUsage: List<Int>
)