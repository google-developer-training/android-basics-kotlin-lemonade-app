/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import java.io.File
import java.nio.file.Path

fun generateVoucherString(projName: String, testDir: Path, testResult: Result): String {
    val lines = getValidTestLinesCount(testDir)
    val obfuscatedLines = rotateLeftInt(lines, lines)
    val obfuscatedName = rot13(projName)
    val obfuscatedResult = obfuscateResult(testResult)
    return "${obfuscatedName}${obfuscatedLines}${obfuscatedResult}"
}

fun getValidTestLinesCount(testDir: Path): Int {
    val testFiles = testDir.toFile().walk().filter { !it.isDirectory }.toList()
    return testFiles.map { f -> countValidLines(f) }.reduce { acc, num -> acc + num }
}

fun countValidLines(file: File): Int {
    var lineCount = 0
    var multiStartCount = 0
    file.forEachLine(action = fun(line: String) {
        val trimmed = line.trim()
        val isLineComment = trimmed.take(2) == "//"
        if (isLineComment) {
            return
        }

        var charCount = 0
        for (i in trimmed.indices) {
            if (i > 0 && trimmed[i - 1] == '/' && trimmed[i] == '*') {
                if (multiStartCount == 0) {
                    charCount--
                }
                multiStartCount++
            } else if (multiStartCount > 0 && i > 0 && trimmed[i - 1] == '*' && trimmed[i] == '/') {
                multiStartCount--
            } else if (multiStartCount == 0 && !trimmed[i].isWhitespace()) {
                charCount++
            }
        }
        if (charCount == 0) {
            return
        }

        lineCount++
    })

    return lineCount
}

fun rot13(str: String): String {
    return str.map{
        if (it.isLetter()) {
            val moved = it.toUpperCase().toInt() + 13
            val rotated = if (moved > 90) {
                moved - 26
            } else moved
            rotated.toChar().toLowerCase()
        } else {
            it
        }
    }.joinToString("")
}

fun rotateLeftInt(num: Int, bitCount: Int): Int = num.shl(bitCount) or num.ushr(32 - bitCount)

fun obfuscateResult(result: Result): String {
    val rotPassed = rotateLeftInt(result.passed, result.passed).toString()
    val rotTotal = rotateLeftInt(result.total, result.total).toString()
    return "${rotPassed}:${rotTotal}"
}
