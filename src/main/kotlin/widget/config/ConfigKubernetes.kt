/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package widget.config

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.module.config.KubernetesConfig
import com.github.shoothzj.dev.storage.StorageK8s

@Composable
fun ConfigKubernetes() {
    val dialogState = mutableStateOf(false)
    val errorState = mutableStateOf("")
    val kubernetes = mutableStateOf(StorageK8s.getInstance().listContent())
    val editKubernetesName = mutableStateOf("default")
    val editKubernetesHost = mutableStateOf("localhost")
    val editKubernetesPort = mutableStateOf(22)
    val editKubernetesUsername = mutableStateOf("root")
    val editKubernetesPassword = mutableStateOf("")
    val editKubernetesRootPassword = mutableStateOf("")
    ConfigBase(
        "kubernetes",
        dialogState,
        errorState,
        dialogInputContent = {
            ConfigItemString(editKubernetesName, "config name")
            ConfigItemString(editKubernetesHost, "config host")
            ConfigItemPort(editKubernetesPort, "config port", errorState)
            ConfigItemString(editKubernetesUsername, "ssh username")
            ConfigItemString(editKubernetesPassword, "ssh password")
            ConfigItemString(editKubernetesRootPassword, "ssh root password(if you need to switch root)")
        },
        dialogConfirm = {
            if (editKubernetesRootPassword.value == "") {
                StorageK8s.getInstance().saveConfig(
                    KubernetesConfig(
                        editKubernetesName.value,
                        editKubernetesHost.value,
                        editKubernetesPort.value,
                        editKubernetesUsername.value,
                        editKubernetesPassword.value,
                    )
                )
            } else {
                StorageK8s.getInstance().saveConfig(
                    KubernetesConfig(
                        editKubernetesName.value,
                        editKubernetesHost.value,
                        editKubernetesPort.value,
                        editKubernetesUsername.value,
                        editKubernetesPassword.value,
                        editKubernetesRootPassword.value,
                    )
                )
            }
            kubernetes.value = StorageK8s.getInstance().listContent()
        },
        content = {
            repeat(kubernetes.value.size) { it ->
                Text(kubernetes.value[it])
            }
        }
    )
}