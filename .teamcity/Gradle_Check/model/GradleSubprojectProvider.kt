/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Gradle_Check.model

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import model.GradleSubproject
import java.io.File
import java.util.function.Supplier

val slowSubprojects = listOf("platformPlay")

val ignoredSubprojects = listOf(
    "soak", // soak test
    "distributions", // build distributions
    "docs", // sanity check
    "architectureTest" // sanity check
)

class JsonGradleSubprojectProvider(private val jsonFile: File) : Supplier<List<GradleSubproject>> {
    override fun get(): List<GradleSubproject> {
        val array = JSON.parseArray(jsonFile.readText()) as JSONArray
        return array.map { toSubproject(it as Map<String, Object>) }
    }

    private
    fun toSubproject(subproject: Map<String, Object>): GradleSubproject {
        val name = subproject["name"] as String
        val unitTests = !ignoredSubprojects.contains(name) && subproject["unitTests"] as Boolean
        val functionalTests = !ignoredSubprojects.contains(name) && subproject["functionalTests"] as Boolean
        val crossVersionTests = !ignoredSubprojects.contains(name) && subproject["crossVersionTests"] as Boolean
        return GradleSubproject(name, unitTests, functionalTests, crossVersionTests, name in slowSubprojects)
    }
}
