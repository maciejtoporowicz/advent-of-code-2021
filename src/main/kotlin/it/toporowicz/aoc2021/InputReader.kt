package it.toporowicz.aoc2021

import java.nio.file.Files
import kotlin.io.path.toPath

object InputReader {
    fun readInputData(fileName: String, clazz: Class<*> = InputReader::class.java): List<String> =
        Files.readAllLines(clazz.classLoader.getResource(fileName).toURI().toPath())
}