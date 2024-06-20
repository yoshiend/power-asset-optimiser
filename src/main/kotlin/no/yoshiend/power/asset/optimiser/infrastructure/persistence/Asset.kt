package no.yoshiend.power.asset.optimiser.infrastructure.persistence

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "assets")
class Asset(
    @Id
    @JsonIgnore
    val id: Long? = null,
    val name: String,
    val dailyPowerUsage: Int,
    val minimumPowerUsage: Int,
    val maximumPowerUsage: Int,
)
