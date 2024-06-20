package no.yoshiend.power.asset.optimiser.app

import no.yoshiend.power.asset.optimiser.domain.AssetScheduler
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.Asset
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.AssetPowerPlan
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.AssetRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assets")
class AssetController(
    private val assetRepository: AssetRepository,
    private val assetScheduler: AssetScheduler
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllAssets(): ResponseEntity<List<Asset>> {
        val assets = assetRepository.findAll().toList()
        return ResponseEntity.ok().body(assets)
    }

    @GetMapping(
        value = ["/{asset-name}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAssetByName(
        @PathVariable("asset-name") assetName: String
    ): ResponseEntity<AssetPowerPlan> {
        return assetRepository.findByName(assetName)?.let {
            ResponseEntity.ok(it.powerPlan)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerAsset(@RequestBody asset: Asset): ResponseEntity<Asset> {
        assetRepository.findByName(asset.name)?.let {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(assetRepository.save(asset));
    }

    @PostMapping(
        value = ["/power-plan"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createPowerPlan(): ResponseEntity<Unit> {
        assetScheduler.scheduleAssets()
        return ResponseEntity.ok().build();
    }
}
