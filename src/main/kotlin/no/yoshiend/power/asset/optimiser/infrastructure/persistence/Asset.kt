package no.yoshiend.power.asset.optimiser.infrastructure.persistence

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.data.relational.core.mapping.Table

@Table(name = "assets")
data class Asset(
    @Id
    @JsonIgnore
    val id: Long? = null,
    val name: String,
    val dailyPowerUsage: Int,
    val minimumPowerUsage: Int,
    val maximumPowerUsage: Int,
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL, prefix = "power_plan_")
    val powerPlan: AssetPowerPlan
)
