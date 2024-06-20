package no.yoshiend.power.asset.optimiser.infrastructure.persistence

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "asset_power_usage")
class PowerUsage(
    @Id
    @JsonIgnore
    val id: Long? = null,
    val hour: Int,
    val usage: Int
)
