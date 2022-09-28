package com.cluster.data.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "deal")
@Slf4j
@Data
@RequiredArgsConstructor
public class Deal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "unique_id", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uniqueId;

    @Column(name = "from_currency", nullable = false)
    private Currency fromCurrency;

    @Column(name = "to_currency", nullable = false)
    private Currency toCurrency;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "amount", precision = 22, scale = 2)
    private BigDecimal amount;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Deal deal = (Deal) o;
        return id != null && Objects.equals(id, deal.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
