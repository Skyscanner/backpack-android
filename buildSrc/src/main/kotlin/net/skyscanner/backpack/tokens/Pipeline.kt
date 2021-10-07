package net.skyscanner.backpack.tokens

interface Pipeline<T> {

  fun execute(): T

}

internal fun <T> pipelineOf(block: () -> T) : Pipeline<T> =
  object : Pipeline<T> {
    override fun execute(): T  =
      block()
  }

internal fun <T, R> Pipeline<T>.pipeTo(transformer: (T) -> R): Pipeline<R> =
  pipelineOf {
    transformer(this@pipeTo.execute())
  }
