package net.skyscanner.backpack.demo.meta

import javax.annotation.concurrent.Immutable

@Immutable
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Component(
  val name: String,
  val link: String,
)
