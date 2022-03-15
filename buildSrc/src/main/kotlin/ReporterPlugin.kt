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
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.VerificationTask

open class ReporterPluginExtension {
    var contentPath: String = ""
    var returnPath: String = ""
}

class ReporterPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("reporter", ReporterPluginExtension::class.java)

        project.task("agpRunTests").doLast {
            val containingDir = project.projectDir.toPath().resolve("build")
            val endingSuffix = "debugAndroidTest.xml"
            val result = readTestResult(containingDir, endingSuffix)

            val testDir = project.projectDir.toPath().resolve("src/androidTest")
            val projName = project.rootProject.name
            val voucher = generateVoucherString(projName, testDir, result)

            val url = generateReportUrl(
                voucher,
                extension.contentPath,
                extension.returnPath,
                result.passed,
                result.total
            )
            openUrlInBrowser(url)
        }.dependsOn("connectedAndroidTest")

        project.tasks.all {
            val isInstrumentationTask = this.name.matches(Regex("connected.*AndroidTest"))
            if (isInstrumentationTask && this is VerificationTask) {
                this.ignoreFailures = true
            }
        }
    }
}
