package com.pivaiot.common.lang.data;


import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseAuditData extends BaseIdAndTimeData {
    private Long createdBy;
    private Long updatedBy;
}
