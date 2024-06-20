package no.yoshiend.power.asset.optimiser.infrastructure.persistence

import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDate

data class AssetPowerPlan(
    val date: LocalDate?,
    @MappedCollection(idColumn = "asset_id", keyColumn = "hour")
    var powerUsage: List<PowerUsage>
)