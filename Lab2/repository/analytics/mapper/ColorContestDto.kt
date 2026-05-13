package ua.nure.smartlight.repository.analytics.mapper

import ua.nure.smartlight.repository.analytics.dto.ColorContestDto
import ua.nure.smartlight.repository.analytics.model.ColorContest

fun ColorContestDto.toModel() =
    ColorContest(
        r = r,
        g = g,
        b = b,
        count = count
    )