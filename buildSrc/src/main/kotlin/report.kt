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
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder

fun generateReportUrl(
    voucher: String,
    contentPath: String,
    returnPath: String,
    passedCount: Int,
    totalCount: Int
): String {
    val params = HashMap<String, String>()
    if (voucher.isNotEmpty()) params["vc"] = voucher
    // continue query param is used to indicate the "go back" functionality on codelabs
    if (returnPath.isNotEmpty()) params["continue"] = returnPath
    params["pc"] = passedCount.toString()
    params["tc"] = totalCount.toString()

    val paramsStr = params
        .map{ "${it.key}=${URLEncoder.encode(it.value, "utf-8")}" }
        .sorted()
        .joinToString("&")
    return "$contentPath?$paramsStr"
}

fun openUrlInBrowser(url: String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(URI(url))
        return
    }

    // Not handled by java.awt.Desktop
    val os = System.getProperty("os.name").toLowerCase()
    val command = if (os.contains("mac")) {
        "open $url"
    } else if (os.contains("nix") || os.contains("nux")) {
        "xdg-open $url"
    } else {
        ""
    }

    val runtime = Runtime.getRuntime()
    try {
        runtime.exec(command)
    } catch (e: Exception) {
        println(e)
        println("Unable to open the browser. Please open this link to register your results: $url")
    }
}
