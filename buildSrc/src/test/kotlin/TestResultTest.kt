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
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class TestResultTest {
    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    @Test
    fun readTestResult_finds_file() {
        val folder = tempFolder.newFolder("a")
        val file = File(folder, "abc_test.kml")
        file.printWriter().use {
            it.println(
                """
                <?xml version='1.0' encoding='UTF-8' ?>
                <testsuite name="abc.def.ghi" tests="3" failures="0" errors="0" skipped="0" time="14.308" timestamp="2021-07-14T18:16:35" hostname="localhost">
                    <properties>
                        <property name="device" value="Pixel_3a_API_30_x86(AVD) - 11" />
                        <property name="flavor" value="debugAndroidTest" />
                        <property name="project" value="app" />
                    </properties>
                    <testcase name="test_abc" classname="abc.def.ghi" time="2.536" />
                    <testcase name="test_def" classname="abc.def.ghi" time="0.25" />
                    <testcase name="test_ghi" classname="abc.def.ghi" time="0.696" />
                </testsuite>
                """.trimStart()
            )
        }
        val suffix = "test.kml"

        val want = Result(3, 3)
        val got = readTestResult(folder.toPath(), suffix)
        Assert.assertEquals(want, got)
    }

    @Test
    fun readTestResult_finds_nested_file_with_one_fail() {
        val root = tempFolder.newFolder("a")
        val nested = File(root, "b")
        nested.mkdir()

        val file = File(nested, "abc_test.kml")
        file.printWriter().use {
            it.println(
                """
                <?xml version='1.0' encoding='UTF-8' ?>
                <testsuite name="abc.def.ghi" tests="3" failures="1" errors="0" skipped="0" time="14.308" timestamp="2021-07-14T18:16:35" hostname="localhost">
                    <properties>
                        <property name="device" value="Pixel_3a_API_30_x86(AVD) - 11" />
                        <property name="flavor" value="debugAndroidTest" />
                        <property name="project" value="app" />
                    </properties>
                    <testcase name="test_abc" classname="abc.def.ghi" time="2.536" />
                    <testcase name="test_def" classname="abc.def.ghi" time="0.25" />
                    <testcase name="test_ghi" classname="abc.def.ghi" time="0.696" />
                </testsuite>
                """.trimStart()
            )
        }
        val suffix = "test.kml"

        val want = Result(2, 3)
        val got = readTestResult(root.toPath(), suffix)
        Assert.assertEquals(want, got)
    }

    @Test
    fun readTestResult_one_fail_one_error_one_skip() {
        val folder = tempFolder.newFolder("a")
        val file = File(folder, "abc_test.kml")
        file.printWriter().use {
            it.println(
                """
                <?xml version='1.0' encoding='UTF-8' ?>
                <testsuite name="abc.def.ghi" tests="3" failures="1" errors="1" skipped="1" time="14.308" timestamp="2021-07-14T18:16:35" hostname="localhost">
                    <properties>
                        <property name="device" value="Pixel_3a_API_30_x86(AVD) - 11" />
                        <property name="flavor" value="debugAndroidTest" />
                        <property name="project" value="app" />
                    </properties>
                    <testcase name="test_abc" classname="abc.def.ghi" time="2.536" />
                    <testcase name="test_def" classname="abc.def.ghi" time="0.25" />
                    <testcase name="test_ghi" classname="abc.def.ghi" time="0.696" />
                </testsuite>
            """.trimStart()
            )
        }
        val suffix = "test.kml"

        val want = Result(0, 3)
        val got = readTestResult(folder.toPath(), suffix)
        Assert.assertEquals(want, got)
    }
}
