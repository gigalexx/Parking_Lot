package parking

import java.util.Scanner
import kotlin.system.exitProcess

var parking = BooleanArray(0)
var status  = Array(0) {Car("", "") }
val scanner = Scanner(System.`in`)
const val REG_BY_COLOR = 1
const val SPOT_BY_COLOR = 0
const val SPOT_BY_REG = 2

fun main(args: Array<String>) {
    letsPark()
}

fun letsPark(){

    while (scanner.hasNext()) when (scanner.next()) {
        "create" -> createBranch()
        "park" -> parkBranch()
        "leave" -> leaveBranch()
        "status" -> statusBranch()
        "reg_by_color" -> regByBranch(REG_BY_COLOR)
        "spot_by_color" -> regByBranch(SPOT_BY_COLOR)
        "spot_by_reg" -> regByBranch(SPOT_BY_REG)
        "exit" -> exitProcess(1)
    }
}

fun regByBranch(regBy: Int) {

    var output = ""
    val colorOrRegNumber = scanner.next()



    if (parking.isNotEmpty()) {
        when (regBy) {
            REG_BY_COLOR -> {
                for (index in status.indices) if (status[index].plateNumber.isNotEmpty() && status[index].color.toLowerCase() == colorOrRegNumber.toLowerCase()) {
                    output += "${status[index].plateNumber}, " // KA-01-HH-9999, KA-01-HH-3672
                }
            }
            SPOT_BY_COLOR -> {
                for (index in status.indices) if (status[index].plateNumber.isNotEmpty() && status[index].color.toLowerCase() == colorOrRegNumber.toLowerCase()) {
                    output += "${index + 1}, " // 1, 2, 3
                }
            }
            SPOT_BY_REG -> {
                for (index in status.indices) if (status[index].plateNumber.isNotEmpty() && status[index].plateNumber.toLowerCase() == colorOrRegNumber.toLowerCase()) {
                    output += "${index + 1}, " // 1, 2, 3
                }
            }
        }


        if(output.isEmpty()){
            when (regBy) {
                REG_BY_COLOR, SPOT_BY_COLOR -> {
                    println("No cars with color $colorOrRegNumber were found.")
                }
                SPOT_BY_REG -> {
                    println("No cars with registration number $colorOrRegNumber were found.")
                }
            }
        } else {
            println(output.trim().substring(0 until output.lastIndex - 1))
        }


    } else println("Sorry, parking lot is not created.")

    letsPark()
}

fun statusBranch() {

    if (parking.isNotEmpty()) {
        var output = ""
        for (index in status.indices) if (status[index].plateNumber.isNotEmpty()) {
            output += "${index + 1} ${status[index]}\n" // 1 KA-01-HH-9999 White
        }

        if (output.isNotEmpty()) {
            print(output)
        } else {
            println("Parking lot is empty.")
        }
    } else {
        println("Sorry, parking lot is not created.")
    }
    letsPark()
}

fun createBranch() {
    val size = scanner.nextInt()
    parking = BooleanArray(size)
    status = Array(size) { Car("","") }

    println("Created a parking lot with ${parking.size} spots.")

}

fun parkBranch(){

    when {
        parking.isNotEmpty() -> {
            val plateNumber = scanner.next()
            val color = scanner.next()

            val car = Car(plateNumber, color)
            var isParked = false

            for (index in parking.indices) if (!isParked && !parking[index]) {
                parking[index] = true
                isParked = true
                println("$color car parked on the spot ${index + 1}.")
                status[index] = car /* KA-01-HH-9999 White */
            }

            if (!isParked) println("Sorry, parking lot is full.")
        } else -> println("Sorry, parking lot is not created.")
    }

    letsPark()
}

fun leaveBranch(){

    when {
        parking.isNotEmpty() -> {
            val parkingSpot = scanner.nextInt()

            if (parkingSpot in 1..parking.size) {
                if (parking[parkingSpot - 1]) {
                    parking[parkingSpot - 1] = false
                    status[parkingSpot - 1] = Car("","")
                    println("Spot $parkingSpot is free.")
                } else {
                    println("There is no car in the spot $parkingSpot.")
                }
            }
        }
        else -> println("Sorry, parking lot is not created.")
    }

    letsPark()
}

data class Car(val plateNumber: String, val color: String){
    override fun toString(): String = "$plateNumber $color"
}