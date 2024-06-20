package no.yoshiend.power.asset.optimiser.app

import no.yoshiend.power.asset.optimiser.domain.AssetPowerPlan
import no.yoshiend.power.asset.optimiser.domain.AssetScheduler
import no.yoshiend.power.asset.optimiser.infrastructure.persistence.Asset
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

    @GetMapping(value = ["/name/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAssetByName(@PathVariable("name") name: String): ResponseEntity<Asset> {
        val assets = assetRepository.findByName(name)
        return if (assets == null) ResponseEntity.notFound().build()
        else ResponseEntity.ok().body(assets)
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerAsset(@RequestBody asset: Asset): ResponseEntity<Asset> {
        return ResponseEntity.ok(assetRepository.save(asset));
    }

    @PostMapping(
        value = ["/power-plan"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createPowerPlan(): ResponseEntity<List<AssetPowerPlan>> {
        return ResponseEntity.ok(assetScheduler.scheduleAssets());
    }
}