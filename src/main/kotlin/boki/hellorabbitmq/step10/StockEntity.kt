package boki.hellorabbitmq.step10

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
class StockEntity(

    private val userId: String,

    private val stock: Int,

    private var processed: Boolean,

    private var createdAt: LocalDateTime? = null,

    private var updatedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
) {
    @JsonGetter("userId")
    fun userId() = userId

    @JsonGetter("id")
    fun id() = id

    fun updateProcessed(processed: Boolean) {
        this.processed = processed
    }

    fun invokeCreated() {
        this.createdAt = LocalDateTime.now()
    }

    fun invokeUpdated() {
        this.updatedAt = LocalDateTime.now()
    }

    override fun toString(): String {
        return "StockEntity(id=$id, userId='$userId', stock=$stock, processed=$processed, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}