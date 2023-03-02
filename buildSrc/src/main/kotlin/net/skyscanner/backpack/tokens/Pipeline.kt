/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
package net.skyscanner.backpack.tokens

import java.io.File

interface Pipeline<T> {

  fun execute(): T
}

fun <T> Pipeline<File>.readAs(format: BpkFormat<T>): Pipeline<T> =
  pipeTo(format)

fun <T> Pipeline<T>.saveTo(output: BpkOutput<T>): Pipeline<Boolean> =
  pipeTo(output)

fun <Input, Output> Pipeline<Input>.parseAs(parser: BpkParser<Input, Output>): Pipeline<Output> =
  pipeTo(parser)

fun <In, Out> Pipeline<In>.transformTo(transformer: BpkTransformer<In, Out>): Pipeline<Out> =
  pipeTo(transformer)

internal fun <T> pipelineOf(block: () -> T): Pipeline<T> =
  object : Pipeline<T> {
    override fun execute(): T =
      block()
  }

internal fun <T, R> Pipeline<T>.pipeTo(transformer: (T) -> R): Pipeline<R> =
  pipelineOf {
    transformer(this@pipeTo.execute())
  }
