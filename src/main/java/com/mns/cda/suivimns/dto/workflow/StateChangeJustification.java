package com.mns.cda.suivimns.dto.workflow;

import jakarta.validation.constraints.Size;

public record StateChangeJustification(
        @Size(max=255) String reason
) {
}
