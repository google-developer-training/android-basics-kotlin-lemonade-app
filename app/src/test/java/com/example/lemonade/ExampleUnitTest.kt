package com.example.lemonade

import com.example.lemonade.data.RoundHut
import com.example.lemonade.data.RoundTower
import com.example.lemonade.data.SquareCabin
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun generates_number() {

        val squareCabin = SquareCabin(6,50.0)

        //println("\nSquare Cabin\n============")
        //println("Capacity: ${squareCabin.capacity}")
        //println("Material: ${squareCabin.buildingMaterial}")
        //println("Has room? ${squareCabin.hasRoom()}")

        with(squareCabin){
            println("\nSquare Cabin\n============")
            println("Capacity: $capacity")
            println("Material: $buildingMaterial")
            println("Has room? ${hasRoom()}")
            println("floorArea? ${floorArea()}")
        }
        val roundHut = RoundHut(3,10.0)
        with(roundHut) {
            println("\nRound Hut\n=========")
            println("Material: ${buildingMaterial}")
            println("Capacity: ${capacity}")
            println("Has room? ${hasRoom()}")
            println("carpet size ${calculateMaxCarpetLength()}")
        }

        val roundTower = RoundTower(4,15.5)
        with(roundTower) {
            println("\nRound Tower\n==========")
            println("Material: ${buildingMaterial}")
            println("Capacity: ${capacity}")
            println("calculateMaxCarpetLength ${calculateMaxCarpetLength()}")
        }
    }


}