package no.yoshiend.power.asset.optimiser

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "power-price", url = "https://www.hvakosterstrommen.no/api/v1/prices/")
interface PowerPriceClient {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{year}/{month}-{day}_{area}.json"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getPrices(
        @PathVariable("year") year: String,
        @PathVariable("month") month: String,
        @PathVariable("day") day: String,
        @PathVariable("area") area: PriceArea
    ): List<GetPowerPriceResponse>
}