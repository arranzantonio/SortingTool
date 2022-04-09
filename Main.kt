package sorting

import java.io.File
import java.io.FileNotFoundException
import java.util.Scanner

fun mergeSortStrings(numbers: MutableList<String>): MutableList<String>  {
    val middle = numbers.size / 2
    val orderedNumbers = mutableListOf<String>()
    if (numbers.size > 1) {
        var firstHalf = numbers.subList(0,middle)
        var secondHalf = numbers.subList(middle, numbers.size)
        firstHalf = mergeSortStrings(firstHalf)
        secondHalf = mergeSortStrings(secondHalf)
        var i = 0
        var j = 0
//      Until we reach either end of either firstHalf or secondHalf, pick larger among
//      elements firstHalf and secondHalf and place them in the correct position at orderedList
        while (i < firstHalf.size && j < secondHalf.size) {
            if (firstHalf[i].lowercase() < secondHalf[j].lowercase()) {
                orderedNumbers.add(firstHalf[i])
                ++i
            } else {
                orderedNumbers.add(secondHalf[j])
                ++j
            }
        }

//      When we run out of elements in either L or M,
//      Pick up the remaining elements and put in A[p..r]
        while (i < firstHalf.size) {
            orderedNumbers.add(firstHalf[i])
            ++i
        }
        while (j < secondHalf.size) {
            orderedNumbers.add(secondHalf[j])
            ++j
        }
    }
    if (numbers.size == 1) orderedNumbers.add(numbers[0])
    return orderedNumbers
}

fun mergeSort(numbers: MutableList<Int>): MutableList<Int>  {
    val middle = numbers.size / 2
    val orderedNumbers = mutableListOf<Int>()
    if (numbers.size > 1) {
        var firstHalf = numbers.subList(0,middle)
        var secondHalf = numbers.subList(middle, numbers.size)
        firstHalf = mergeSort(firstHalf)
        secondHalf = mergeSort(secondHalf)
        var i = 0
        var j = 0
//      Until we reach either end of either firstHalf or secondHalf, pick larger among
//      elements firstHalf and secondHalf and place them in the correct position at orderedList
        while (i < firstHalf.size && j < secondHalf.size) {
            if (firstHalf[i] < secondHalf[j]) {
                orderedNumbers.add(firstHalf[i])
                ++i
            } else {
                orderedNumbers.add(secondHalf[j])
                ++j
            }
        }

//      When we run out of elements in either L or M,
//      Pick up the remaining elements and put in A[p..r]
        while (i < firstHalf.size) {
            orderedNumbers.add(firstHalf[i])
            ++i
        }
        while (j < secondHalf.size) {
            orderedNumbers.add(secondHalf[j])
            ++j
        }
    }
    if (numbers.size == 1) orderedNumbers.add(numbers[0])
    return orderedNumbers
}



fun help() {
    println("Wrong arguments. Try it again.")
    println("Usage: java -dataType word|line|numbers [-sortingType natural|byCount]")
}

fun processArguments(validArguments: MutableList<String>): Pair<String, String> {
    val argsToRemove = mutableListOf<String>()
    val dataTypes = arrayOf("word", "line", "long")
    val sortingTypes = arrayOf("natural", "byCount")
    val parameters = arrayOf("-sortingType", "-dataType")

    // This code snippet cleans args of invalid args
    for (i in validArguments) {
        if (!(i in dataTypes || i in sortingTypes || i in parameters)) {
            println("\"$i\" is not a valid parameter. It will be skipped.")
            argsToRemove.add(i)
        }
    }

    validArguments.removeAll(argsToRemove)


    if (("-sortingType" in validArguments) && !("natural" in validArguments) && !("byCount" in validArguments)) {
        println("No sorting type defined!")
        return "bad" to "bad"
    }
    if (("-dataType" in validArguments) && !("word" in validArguments) && !("long" in validArguments) && !("line" in validArguments)) {
        println("No data type defined!")
        return "bad" to "bad"
    }

    return when (validArguments.size) {
        2 ->    if (validArguments[0] == "-dataType" && validArguments[1] in dataTypes) validArguments[1] to "natural"
        else "bad"  to "bad"

        4 ->    if (
            "-sortingType" in validArguments &&
            "-dataType" in validArguments &&
            validArguments[validArguments.indexOf("-sortingType")+1] in sortingTypes &&
            validArguments[validArguments.indexOf("-dataType") + 1] in dataTypes
        ) validArguments[validArguments.indexOf("-dataType") + 1] to validArguments[validArguments.indexOf("-sortingType")+1]
        else "bad" to "bad"
        else -> "bad" to "bad"
    }
}

fun getNumbers(scanner: Scanner): MutableList<Int> {
    val numbers = mutableListOf<Int>()
    val regex = "\\s+".toRegex()
    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        val numbersLine = line.split(regex)
        for (n in numbersLine) {
            try {
                numbers.add(n.toInt())
            } catch (e: NumberFormatException) {
                println("\"$n\" is not a long. It will be skipped.")
            }
        }
    }
    return numbers
}

fun getWords(scanner: Scanner): MutableList<String> {
    val words = mutableListOf<String>()
    val regex = "\\s+".toRegex()
    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        val wordsLine = line.split(regex)
        for (w in wordsLine) {
            words.add(w)
        }
    }
    return words
}

fun getLines(scanner: Scanner): MutableList<String> {
    val lines = mutableListOf<String>()
    while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine())
    }
    return lines
}


fun main(args: Array<String>) {
    // First of all process args searchin for output and input files and cleaning

    var validArguments = args.toMutableList()
    var inputFileName = ""
    var outputFileName = ""

    if ("-inputFile" in validArguments) {
        try {
            inputFileName = validArguments.get(validArguments.indexOf("-inputFile") + 1)
            val inputFile = File(inputFileName)
            inputFile.readText()
            validArguments.remove("-inputFile")
            validArguments.remove(inputFileName)
        }
        catch (e: IndexOutOfBoundsException) {
            println(e)
            println("Input file name must be specified.")
        }
        catch (e: FileNotFoundException) {
            println(e.message)
        }
    }

    if ("-outputFile" in validArguments) {
        try {
            outputFileName = validArguments.get(validArguments.indexOf("-outputFile") + 1)
            val outputFile = File(outputFileName)
            outputFile.writeText("")
            validArguments.remove("-outputFile")
            validArguments.remove(outputFileName)
        }
        catch (e: IndexOutOfBoundsException) {
            println(e)
            println("Output file name must be specified.")
        }
        catch (e: FileNotFoundException) {
            println(e.message)
        }
    }


    val options = processArguments(validArguments)
    if (options.first == "bad") help()
    else {
        when (options.first) {
            "long" -> {
                val numbers =   if(inputFileName == "") getNumbers(Scanner(System.`in`))
                else {
                    val inputFile = File(inputFileName)
                    getNumbers(Scanner(inputFile))
                }
                println("Total numbers: ${numbers.size}")
                when (options.second) {
                    "byCount" -> {
                        val mapFreq = mutableMapOf<Int, Int>()
                        for (i in numbers) {
                            val times = numbers.count { it == i }
                            mapFreq[i] = times
                        }
                        val freq = mergeSort(mapFreq.values.toMutableList()).distinct()
                        for (i in freq) {
                            // obtengo las claves de mapFreq que coinciden con cada valor de frecuencia
                            // y las ordeno según su valor de string
                            val equalFreq = mergeSort(mapFreq.filter { it.value == i }.keys.toMutableList())
                            for (j in equalFreq) {
                                println("${j}: $i time(s), ${i * 100 / numbers.size}%")
                            }
                        }
                    }
                    "natural" -> {
                        print("Sorted data: ")
                        for (i in mergeSort(numbers)) print("$i ")
                    }
                }
            }
            "word" -> {
                val words = if(inputFileName == "") getWords(Scanner(System.`in`))
                else {
                    val inputFile = File(inputFileName)
                    getWords(Scanner(inputFile))
                }

                println("Total words: ${words.size}.")
                when (processArguments(validArguments).second) {
                    "byCount" -> {
                        // mapFreq almacena un mapa con parejas "palabra" -> frecuencia
                        val mapFreq = mutableMapOf<String, Int>()
                        for (i in words) {
                            val times = words.count { it.lowercase() == i.lowercase() }
                            mapFreq[i.lowercase()] = times
                        }
                        // Obtengo una lista de las frecuencias, las ordeno y elimino duplicaados
                        val freq = mergeSort(mapFreq.values.toMutableList()).distinct()
                        for (i in freq) {
                            // obtengo las claves de mapFreq que coinciden con cada valor de frecuencia
                            // y las ordeno según su valor de string
                            val equalFreq = mergeSortStrings(mapFreq.filter { it.value == i }.keys.toMutableList())
                            for (j in equalFreq) {
                                println("${j}: $i time(s), ${i * 100 / words.size}%")
                            }
                        }
                    }
                    "natural" -> {
                        print("Sorted data: ")
                        for (i in mergeSortStrings(words)) print("$i ")
                    }
                }
            }
            "line" -> {
                val lines = if(inputFileName == "") getLines(Scanner(System.`in`))
                else {
                    val inputFile = File(inputFileName)
                    getLines(Scanner(inputFile))
                }

                println("Total lines: ${lines.size}.")
                when (processArguments(validArguments).second) {
                    "byCount" -> {
                        // mapFreq almacena un mapa con parejas "palabra" -> frecuencia
                        val mapFreq = mutableMapOf<String, Int>()
                        for (i in lines) {
                            val times = lines.count { it.lowercase() == i.lowercase() }
                            mapFreq[i.lowercase()] = times
                        }
                        // Obtengo una lista de las frecuencias, las ordeno y elimino duplicaados
                        val freq = mergeSort(mapFreq.values.toMutableList()).distinct()
                        for (i in freq) {
                            // obtengo las claves de mapFreq que coinciden con cada valor de frecuencia
                            // y las ordeno según su valor de string
                            val equalFreq = mergeSortStrings(mapFreq.filter { it.value == i }.keys.toMutableList())
                            for (j in equalFreq) {
                                println("${j}: $i time(s), ${i * 100 / lines.size}%")
                            }
                        }
                    }
                    "natural" -> {
                        print("Sorted data: ")
                        for (i in mergeSortStrings(lines)) println("$i ")
                    }
                }
            }
            else -> println("Second argument must be: long, word or line. Try it again.")
        }
    }
}