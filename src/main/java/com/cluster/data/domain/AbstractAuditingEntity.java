package com.cluster.data.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Data
@RequiredArgsConstructor
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = -8241585773957143683L;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "BIGINT default 0")
    private Long version = 0L;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of(ZoneId.systemDefault().getId()));

    @UpdateTimestamp
    @Column(name = "updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime updatedAt;

}
