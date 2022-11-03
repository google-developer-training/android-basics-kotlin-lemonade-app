package com.example.lemonade.data

import java.lang.Math.PI

open class RoundHut(residents:Int, val radius:Double) : Dwelling(residents) {
    override val buildingMaterial: String
        get() = "Straw"
    override val capacity: Int
        get() = 4

    override fun floorArea(): Double {
       return PI * radius * radius
    }

    fun calculateMaxCarpetLength():Double{
        return kotlin.math.sqrt(2.0) * radius
    }
}