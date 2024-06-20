package no.yoshiend.power.asset.optimiser

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
        return powerPriceClient.getPrices("2024", "06","20", PriceArea.NO1)
    }
}