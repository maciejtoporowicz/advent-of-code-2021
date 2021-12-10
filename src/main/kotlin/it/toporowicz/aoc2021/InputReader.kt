package it.toporowicz.aoc2021

import java.nio.file.Files
import kotlin.io.path.toPath

object InputReader {
    fun readInputData(fileName: String): List<String> =
        Files.readAllLines(InputReader::class.java.getResource(fileName).toURI().toPath())
}