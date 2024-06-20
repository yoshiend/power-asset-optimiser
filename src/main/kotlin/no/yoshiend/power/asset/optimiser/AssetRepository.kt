package no.yoshiend.power.asset.optimiser

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : CrudRepository<Asset, Long> {
    fun findByName(name: String): Asset?
}