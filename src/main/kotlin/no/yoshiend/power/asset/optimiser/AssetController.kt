package no.yoshiend.power.asset.optimiser

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assets")
class AssetController(
    private val assetRepository: AssetRepository
) {

    @GetMapping
    fun getAsset(): ResponseEntity<Asset> {
        val testAsset = Asset(
            name = "Asset 1",
            powerUsage24Hours = 100,
            minimumPowerUsage = 50,
            maximumPowerUsage = 150,
        )
        return ResponseEntity.ok().body(testAsset)
    }
    @PostMapping
    fun registerAsset() {
        val testAsset = Asset(
            name = "Asset 1",
            powerUsage24Hours = 100,
            minimumPowerUsage = 50,
            maximumPowerUsage = 150,
        )
        assetRepository.save(testAsset)
    }
}