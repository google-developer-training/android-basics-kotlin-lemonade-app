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

class VoucherTest {
    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    @Test
    fun countValidLines_empty_file() {
        val file = tempFolder.newFile("a.kt")
        val want = 0
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_multi_line_file() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("hello")
            it.println("kotlin")
        }

        val want = 2
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_file_with_empty_lines() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("")
        }

        val want = 0
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_multi_line_with_empty_lines() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("hello")
            it.println("")
            it.println("kotlin")
        }

        val want = 2
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_line_comment() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("hello")
            it.println("")
            it.println("//kotlin")
        }

        val want = 1
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_multi_line_comment() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("/*hello")
            it.println("")
            it.println("kotlin*/")
        }

        val want = 0
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_single_line_multi_line_comment() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("/*hello*/")
            it.println("")
            it.println("kotlin")
        }

        val want = 1
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_partial_line_multi_line_comment() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("/*he*/llo")
            it.println("")
            it.println("kotlin")
        }

        val want = 2
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_multi_line_comment_more_than_one_for_all_line() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("/*he*/ /*llo*/")
            it.println("")
            it.println("kotlin")
        }

        val want = 1
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_multi_line_comment_break_before_end() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println("")
            it.println("/*hello")
            it.println("")
            it.println("kot*/lin")
        }

        val want = 1
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun countValidLines_ignore_whitespace() {
        val file = tempFolder.newFile("a.kt")
        file.printWriter().use {
            it.println(" ")
            it.println("     hello")
            it.println("world ")
            it.println("           ")
            it.println("    hello    kotlin ")
        }

        val want = 3
        val got = countValidLines(file)
        Assert.assertEquals(want, got)
    }

    @Test
    fun rot13_capitals() {
        val want = "nop"
        val got = rot13("ABC")
        Assert.assertEquals(want, got)
    }

    @Test
    fun rot13_rotate() {
        val want = "klm"
        val got = rot13("XyZ")
        Assert.assertEquals(want, got)
    }

    @Test
    fun rot13_skip_non_letters() {
        val want = "nop123kl m"
        val got = rot13("abc123XY Z")
        Assert.assertEquals(want, got)
    }

    @Test
    fun rotateLeftInt_rotate_two() {
        val want = 20
        val got = rotateLeftInt(5, 2)
        Assert.assertEquals(want, got)
    }

    @Test
    fun rotateLeftInt_no_rotation() {
        val want = 123
        val got = rotateLeftInt(123, 0)
        Assert.assertEquals(want, got)
    }

    @Test
    fun rotateLeftInt_wrap_rotation() {
        val want = 31488
        val got = rotateLeftInt(123, 1000)
        Assert.assertEquals(want, got)
    }
}
