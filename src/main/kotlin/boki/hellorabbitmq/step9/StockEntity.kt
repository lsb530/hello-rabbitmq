package boki.hellorabbitmq.step9

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class StockEntity(

    val userId: String,

    val stock: Int,

    var processed: Boolean,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun toString(): String {
        return "StockEntity(id=$id, userId='$userId', stock=$stock, processed=$processed, createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    fun invokeUpdated() {
        this.updatedAt = LocalDateTime.now()
    }

    fun updateProcessed(processed: Boolean) {
        this.processed = processed
        // if (processed) {
        //     invokeUpdated()
        // }
    }
}