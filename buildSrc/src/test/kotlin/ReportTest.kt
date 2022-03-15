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
import org.junit.Test
import org.junit.Assert

class ReportTest {
    @Test
    fun generateReportUrl_params_encoded() {
        val want = "https://def.com?continue=https%3A%2F%2Fghi.com&pc=1&tc=3&vc=abc%3F"
        val got = generateReportUrl("abc?", "https://def.com", "https://ghi.com", 1, 3)
        Assert.assertEquals(want, got)
    }

    @Test
    fun generateReportUrl_empty_voucher() {
        val want = "https://def.com?continue=https%3A%2F%2Fghi.com&pc=1&tc=3"
        val got = generateReportUrl("", "https://def.com", "https://ghi.com", 1, 3)
        Assert.assertEquals(want, got)
    }

    @Test
    fun generateReportUrl_empty_testResult() {
        val want = "https://def.com?continue=https%3A%2F%2Fghi.com&pc=0&tc=0&vc=abc"
        val got = generateReportUrl("abc", "https://def.com", "https://ghi.com", 0, 0)
        Assert.assertEquals(want, got)
    }
}
