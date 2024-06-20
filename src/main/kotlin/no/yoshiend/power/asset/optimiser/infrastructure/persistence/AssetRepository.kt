package no.yoshiend.power.asset.optimiser.infrastructure.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : CrudRepository<Asset, Long> {
    fun findByName(name: String): Asset?
}