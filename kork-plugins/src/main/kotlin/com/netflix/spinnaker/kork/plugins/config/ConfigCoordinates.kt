/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.kork.plugins.config

private const val ROOT_PATH = "/spinnaker/extensibility"

sealed class ConfigCoordinates {
  abstract fun toPointer(): String
}

/**
 * Config coordinates for a plugin's extension.
 */
class ExtensionConfigCoordinates(
  val pluginId: String,
  private val extensionConfigId: String,
  private val extensionsNamespace: String = "extensions"
) : ConfigCoordinates() {
  override fun toPointer(): String {
    val coords = if (extensionConfigId.isEmpty()) listOf(pluginId, extensionsNamespace) else listOf(pluginId, extensionsNamespace, extensionConfigId)
    return coords.let {
      "$ROOT_PATH/plugins/${it.joinToString("/").replace(".", "/")}/config"
    }
  }
}

/**
 * Config coordinates for a plugin.
 */
class PluginConfigCoordinates(
  val pluginId: String,
  private val pluginConfigId: String
) : ConfigCoordinates() {
  override fun toPointer(): String {
    val coords = if (pluginConfigId.isEmpty()) listOf(pluginId) else listOf(pluginId, pluginConfigId)
    return coords.let {
      "$ROOT_PATH/plugins/${it.joinToString("/").replace(".", "/")}/config"
    }
  }
}

/**
 * Config coordinates for a system extension.
 */
class SystemExtensionConfigCoordinates(
  private val extensionConfigId: String
) : ConfigCoordinates() {
  override fun toPointer(): String =
    "$ROOT_PATH/extensions/${extensionConfigId.replace(".", "/")}/config"
}

/**
 * Config coordinates for plugin repository configs.
 */
class RepositoryConfigCoordinates : ConfigCoordinates() {
  override fun toPointer(): String =
    "$ROOT_PATH/repositories"
}
