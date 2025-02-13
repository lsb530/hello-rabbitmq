package boki.hellorabbitmq.step10

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository: JpaRepository<StockEntity, Long>