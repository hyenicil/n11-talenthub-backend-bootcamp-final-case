package com.n11.userservice.common.base;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mehmet Akif Tanisik
 */

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}
