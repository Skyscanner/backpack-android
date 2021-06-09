/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.lint.check

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class BpkComponentUsageDetector : Detector(), SourceCodeScanner, XmlScanner {

  companion object {
    val ISSUE = Issue.create(
      id = "BpkComponentUsage",
      briefDescription = "A Backpack version of this component is available",
      explanation = "Use Backpack components where available to improve consistency, accessibility and dark mode support.",
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        BpkComponentUsageDetector::class.java,
        Scope.JAVA_AND_RESOURCE_FILES
      )
    )

    private val APPLICABLE_TYPES = Component.values().flatMap { it.componentsToReplace }
    private val APPLICABLE_METHODS = Component.values().flatMap { it.staticMethodsToReplace }
  }

  private val classCache = mutableListOf<String>()

  override fun getApplicableConstructorTypes(): List<String> = APPLICABLE_TYPES

  override fun applicableSuperClasses(): List<String> = APPLICABLE_TYPES

  override fun getApplicableElements(): Collection<String> = APPLICABLE_TYPES

  override fun getApplicableMethodNames(): List<String> = APPLICABLE_METHODS

  override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.LAYOUT

  override fun visitClass(context: JavaContext, declaration: UClass) {
    val superClass = declaration.javaPsi.superClass?.qualifiedName ?: return
    val component = Component.find(superClass)
    if (component != null) {
      if (declaration.name == null || classCache.contains(declaration.name)) {
        // in some cases we might have two relevant super classes (e.g. when there's an AppCompat and default component) - only report issue once
        return
      }
      classCache.add(declaration.name!!)
      context.report(context.getNameLocation(declaration), superClass, component)
    }
  }

  override fun visitConstructor(context: JavaContext, node: UCallExpression, constructor: PsiMethod) {
    val className = node.returnType?.canonicalText ?: return
    val component = Component.find(className)
    if (component != null) {
      context.report(context.getLocation(node), className, component)
    }
  }

  override fun visitElement(context: XmlContext, element: Element) {
    val component = Component.find(element.tagName)
    if (component != null) {
      context.report(context.getLocation(element), element.tagName, component)
    }
  }

  override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    if (!context.evaluator.isStatic(method)) {
      return
    }
    val className = method.containingClass?.qualifiedName ?: return
    val component = Component.findMethod(method.name, className)
    if (component != null) {
      context.report(context.getLocation(node), className, component)
    }
  }

  private fun Context.report(location: Location, existingComponent: String, component: Component) {
    report(
      ISSUE,
      location,
      "Backpack component available for $existingComponent. Use ${component.fullName} instead. More info at ${component.url}"
    )
  }

  private enum class Component(
    val fullName: String,
    val webName: String,
    val componentsToReplace: Set<String>,
    val staticMethodsToReplace: Set<String> = emptySet(),
  ) {
    BOTTOM_NAV(
      fullName = "net.skyscanner.backpack.bottomnav.BpkBottomNav",
      webName = "bottom-nav",
      componentsToReplace = setOf("com.google.android.material.bottomnavigation.BottomNavigationView")
    ),
    BOTTOM_SHEET(
      fullName = "net.skyscanner.backpack.bottomsheet.BpkBottomSheetBehaviour",
      webName = "bottom-sheet",
      componentsToReplace = setOf("com.google.android.material.bottomsheet.BottomSheetBehaviour")
    ),
    BUTTON(
      fullName = "net.skyscanner.backpack.button.BpkButton",
      webName = "button",
      componentsToReplace = setOf("android.widget.Button", "Button", "androidx.appcompat.widget.AppCompatButton")
    ),
    CALENDAR(
      fullName = "net.skyscanner.backpack.calendar.BpkCalendar",
      webName = "calendar",
      componentsToReplace = setOf("java.util.Calendar", "com.google.android.material.datepicker.MaterialDatePicker.Builder"),
      staticMethodsToReplace = setOf("datePicker", "dateRangePicker")
    ),
    CARD(
      fullName = "net.skyscanner.backpack.card.BpkCardView",
      webName = "card",
      componentsToReplace = setOf("androidx.cardview.widget.CardView")
    ),
    CHECKBOX(
      fullName = "net.skyscanner.backpack.checkbox.BpkCheckbox",
      webName = "checkbox",
      componentsToReplace = setOf("android.widget.Checkbox", "Checkbox", "androidx.appcompat.widget.AppCompatCheckBox")
    ),
    CHIP(
      fullName = "net.skyscanner.backpack.chip.BpkChip",
      webName = "chip",
      componentsToReplace = setOf("com.google.android.material.chip.Chip")
    ),
    DIALOG(
      fullName = "net.skyscanner.backpack.dialog.BpkDialog",
      webName = "dialog",
      componentsToReplace = setOf("android.app.Dialog")
    ),
    FAB(
      fullName = "net.skyscanner.backpack.fab.BpkFab",
      webName = "floating-action-button",
      componentsToReplace = setOf("com.google.android.material.floatingactionbutton.FloatingActionButton")
    ),
    HORIZONTAL_NAV(
      fullName = "net.skyscanner.backpack.horisontalnav.BpkHorizontalNav",
      webName = "horizontal-nav",
      componentsToReplace = setOf("com.google.android.material.tabs.TabLayout")
    ),
    NAV_BAR(
      fullName = "net.skyscanner.backpack.navbar.BpkNavBar",
      webName = "navigation-bar",
      componentsToReplace = setOf("com.google.android.material.appbar.CollapsingToolbarLayout")
    ),
    RADIO_BUTTON(
      fullName = "net.skyscanner.backpack.radiobutton.BpkRadioButton",
      webName = "radiobutton",
      componentsToReplace = setOf(
        "android.widget.RadioButton",
        "RadioButton",
        "androidx.appcompat.widget.AppCompatRadioButton"
      )
    ),
    SLIDER(
      fullName = "net.skyscanner.backpack.slider.BpkSlider",
      webName = "slider",
      componentsToReplace = setOf(
        "com.google.android.material.slider.RangeSlider",
        "com.google.android.material.slider.Slider",
        "android.widget.SeekBar",
        "SeekBar",
        "androidx.appcompat.widget.AppCompatSeekBar"
      )
    ),
    SNACKBAR(
      fullName = "net.skyscanner.backpack.snackbar.BpkSnackbar",
      webName = "snackbar",
      componentsToReplace = setOf("com.google.android.material.snackbar.Snackbar"),
      staticMethodsToReplace = setOf("make")
    ),
    SPINNER(
      fullName = "net.skyscanner.backpack.spinner.BpkSpinner",
      webName = "spinner",
      componentsToReplace = setOf("android.widget.ProgressBar", "ProgressBar")
    ),
    SWITCH(
      fullName = "net.skyscanner.backpack.toggle.BpkSwitch",
      webName = "switch",
      componentsToReplace = setOf(
        "android.widget.Switch",
        "Switch",
        "androidx.appcompat.widget.SwitchCompat",
        "com.google.android.material.switchmaterial.SwitchMaterial"
      )
    ),
    TEXT(
      fullName = "net.skyscanner.backpack.text.BpkText",
      webName = "text",
      componentsToReplace = setOf("android.widget.TextView", "TextView", "androidx.appcompat.widget.AppCompatTextView")
    ),
    TOAST(
      fullName = "net.skyscanner.backpack.toast.BpkToast",
      webName = "toast",
      componentsToReplace = setOf("android.widget.Toast"),
      staticMethodsToReplace = setOf("makeText")
    ),
    ;

    val url: String = "https://backpack.github.io/components/$webName"

    companion object {
      internal fun find(componentToReplace: String) =
        values().firstOrNull { it.componentsToReplace.contains(componentToReplace) }

      internal fun findMethod(method: String, componentClass: String) =
        values().firstOrNull { it.staticMethodsToReplace.contains(method) && it.componentsToReplace.contains(componentClass) }
    }
  }
}
