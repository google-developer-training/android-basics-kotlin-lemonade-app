package com.example.lemonade.data

class RoundTower(
    private val residents: Int,
    radius: Double,
    val floors: Int = 2) : RoundHut(residents, radius) {



    override val buildingMaterial: String
        get() = "Stone"

    override val capacity: Int
        get() = 4 * floors

    override fun floorArea(): Double {
        return super.floorArea() * floors
    }
}