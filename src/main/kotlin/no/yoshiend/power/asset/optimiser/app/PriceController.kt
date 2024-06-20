package no.yoshiend.power.asset.optimiser.app

import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.GetPowerPriceResponse
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PowerPriceClient
import no.yoshiend.power.asset.optimiser.infrastructure.powerclient.PriceArea
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/prices")
class PriceController(
    private val powerPriceClient: PowerPriceClient
) {

    @GetMapping
    fun getPrices(): List<GetPowerPriceResponse> {
        return powerPriceClient.getPrices("2024", "06","21", PriceArea.NO1).sortedBy { it.timeStart }
    }
}