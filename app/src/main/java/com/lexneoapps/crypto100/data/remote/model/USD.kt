package com.lexneoapps.crypto100.data.remote.model

import kotlin.math.roundToInt

data class USD(
    val market_cap: Double,
    val market_cap_dominance: Double,
    val price: Double
) {

    val capInBil: Int
        get() = (market_cap / 1000000000.00).roundToInt()

    val priceFormatted : Double
            get() = (price * 1000).roundToInt() /1000.00

    val capPercentage: Double
    get() = (market_cap_dominance * 100).roundToInt() / 100.00

}