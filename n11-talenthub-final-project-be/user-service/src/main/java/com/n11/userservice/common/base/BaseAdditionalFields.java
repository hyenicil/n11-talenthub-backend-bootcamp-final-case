package com.n11.userservice.common.base;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * @author Mehmet Akif Tanisik
 */

@Getter
@Setter
@Embeddable
public class BaseAdditionalFields {
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
