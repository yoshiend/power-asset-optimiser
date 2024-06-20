package no.yoshiend.power.asset.optimiser

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "assets")
class Asset(
    @Id
    val id: Long? = null,
    val name: String,
    val powerUsage24Hours: Int,
    val minimumPowerUsage: Int,
    val maximumPowerUsage: Int,
)
