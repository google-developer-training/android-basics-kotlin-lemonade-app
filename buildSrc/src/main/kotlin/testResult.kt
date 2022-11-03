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
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import javax.xml.parsers.DocumentBuilderFactory

class FileNotFoundException : Exception("Cannot find instrumentation test results xml")
class InvalidXmlException : Exception("Test results xml is invalid")

data class Result(val passed: Int, val total: Int)

fun readTestResult(containingDir: Path, endingSuffix: String): Result {
    val found = Files
        .walk(containingDir)
        .filter { f -> f.toString().endsWith(endingSuffix) }
        .collect(Collectors.toList())

    if (found.size != 1) {
        throw FileNotFoundException()
    }
    val firstFile = found.first().toFile()

    val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(firstFile)
    xmlDoc.documentElement.normalize()

    val tags = xmlDoc.getElementsByTagName("testsuite")
    if (tags.length != 1) {
        throw InvalidXmlException()
    }

    var total = 0
    var notPassed = 0
    for(i in 0 until tags.length) {
        val attr = tags.item(i).attributes
        total += attr.getNamedItem("tests").nodeValue.toInt()
        notPassed += attr.getNamedItem("failures").nodeValue.toInt()
        notPassed += attr.getNamedItem("errors").nodeValue.toInt()
        notPassed += attr.getNamedItem("skipped").nodeValue.toInt()
    }

    return Result(total - notPassed, total)
}
