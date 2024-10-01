package com.insact.timeapp.data.mapper

import com.insact.timeapp.data.dto.TimeZoneDto
import com.insact.timeapp.domain.model.TimeZoneDomainModel

fun TimeZoneDto?.toDomain() =
    TimeZoneDomainModel(
        capital = this?.timeZone.orEmpty(),
        time = this?.currentLocalTime.orEmpty()
    )