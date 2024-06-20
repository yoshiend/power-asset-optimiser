package no.yoshiend.power.asset.optimiser

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
    private val assetRepository: AssetRepository
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
        //todo: validate inputs
        return ResponseEntity.ok(assetRepository.save(asset));
    }
}