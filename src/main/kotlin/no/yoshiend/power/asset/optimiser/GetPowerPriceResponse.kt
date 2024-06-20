package no.yoshiend.power.asset.optimiser

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.OffsetTime

data class GetPowerPriceResponse(
    @JsonProperty("NOK_per_kWh")
    val pricePerKwhNok: Double,
    @JsonProperty("EUR_per_kWh")
    val pricePerKwhEur: Double,
    @JsonProperty("EXR")
    val expires: Double,
    @JsonProperty("time_start")
    val timeStart: OffsetDateTime,
    @JsonProperty("time_end")
    val timeEnd: OffsetDateTime
)
