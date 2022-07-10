package com.example.lemonade.data

class SquareCabin(residents:Int,val length: Double) : Dwelling(residents) {
    override val buildingMaterial: String
        get() = "Wood"
    override val capacity: Int
        get() = 6


    override fun floorArea(): Double {
        return length * length
    }
}