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

package net.skyscanner.backpack.lint.check

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

@Suppress("UnstableApiUsage")
class BpkComposeComponentUsageDetector : Detector(), SourceCodeScanner {

  companion object {
    val ISSUE = Issue.create(
      id = "BpkComposeComponentUsage",
      briefDescription = "A Backpack version of this component is available",
      explanation = "Use Backpack components where available to improve consistency, accessibility and dark mode support.",
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        BpkComposeComponentUsageDetector::class.java,
        Scope.JAVA_FILE_SCOPE
      )
    )

    private val APPLICABLE_TYPES =
      Component.values().flatMap { it.componentsToReplace.map { component -> component.substringAfterLast('.') } }
  }

  override fun getApplicableMethodNames(): List<String> {
    return APPLICABLE_TYPES
  }

  override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    val methodPackage = context.evaluator.getPackage(method)!!.qualifiedName
    val qualifiedName = "$methodPackage.${node.methodName}"
    val component = Component.find(qualifiedName)
    if (component != null) {
      context.report(
        ISSUE,
        context.getLocation(node),
        "Backpack component available for $qualifiedName. Use ${component.fullName} instead. More info at ${component.url}"
      )
    }
  }

  private enum class Component(
    val fullName: String,
    val webName: String,
    val componentsToReplace: Set<String>,
  ) {
    BOTTOM_SHEET(
      fullName = "net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheet",
      webName = "bottom-sheet",
      componentsToReplace = setOf("androidx.compose.material.BottomSheetScaffold"),
    ),
    BUTTON(
      fullName = "net.skyscanner.backpack.compose.button.BpkButton",
      webName = "button",
      componentsToReplace = setOf(
        "androidx.compose.material.Button",
        "androidx.compose.material.TextButton",
        "androidx.compose.material.OutlinedButton"
      )
    ),
    CARD(
      fullName = "net.skyscanner.backpack.compose.card.BpkCard",
      webName = "card",
      componentsToReplace = setOf("androidx.compose.material.Card")
    ),
    CHECKBOX(
      fullName = "net.skyscanner.backpack.compose.checkbox.BpkCheckbox",
      webName = "checkbox",
      componentsToReplace = setOf(
        "androidx.compose.material.Checkbox",
        "androidx.compose.material.TriStateCheckbox",
      )
    ),
    DIALOG(
      fullName = "net.skyscanner.backpack.compose.dialog.BpkDialog",
      webName = "dialog",
      componentsToReplace = setOf("androidx.compose.ui.window.Dialog", "androidx.compose.material.AlertDialog")
    ),
    FAB(
      fullName = "net.skyscanner.backpack.compose.fab.BpkFab",
      webName = "floating-action-button",
      componentsToReplace = setOf(
        "androidx.compose.material.FloatingActionButton",
        "androidx.compose.material.ExtendedFloatingActionButton",
      )
    ),
    FLOATING_NOTIFICATION(
      fullName = "net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification",
      webName = "floating-notification",
      componentsToReplace = setOf(
        "androidx.compose.material.Snackbar",
        "androidx.compose.material.SnackbarHost",
      )
    ),
    ICON(
      fullName = "net.skyscanner.backpack.compose.icon.BpkIcon",
      webName = "icon",
      componentsToReplace = setOf(
        "androidx.compose.material.Icon",
      )
    ),
    HORIZONTAL_NAV(
      fullName = "net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav",
      webName = "horizontal-nav",
      componentsToReplace = setOf(
        "androidx.compose.material.Tab",
        "androidx.compose.material.TabRow",
        "androidx.compose.material.ScrollableTabRow",
      )
    ),
    NAV_BAR(
      fullName = "net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar",
      webName = "navigation-bar",
      componentsToReplace = setOf("androidx.compose.material.TopAppBar")
    ),
    RADIO_BUTTON(
      fullName = "net.skyscanner.backpack.compose.radiobutton.BpkRadioButton",
      webName = "radio-button",
      componentsToReplace = setOf("androidx.compose.material.RadioButton")
    ),
    SWITCH(
      fullName = "net.skyscanner.backpack.compose.switch.BpkSwitch",
      webName = "switch",
      componentsToReplace = setOf("androidx.compose.material.Switch")
    ),
    TEXT(
      fullName = "net.skyscanner.backpack.compose.text.BpkText",
      webName = "text",
      componentsToReplace = setOf("androidx.compose.material.Text")
    ),
    TEXT_FIELD(
      fullName = "net.skyscanner.backpack.compose.textfield.BpkTextField",
      webName = "text-input",
      componentsToReplace = setOf(
        "androidx.compose.foundation.text.BasicTextField",
        "androidx.compose.material.OutlinedTextField",
        "androidx.compose.material.TextField",
      )
    ),
    SPINNER(
      fullName = "net.skyscanner.backpack.compose.spinner.BpkSpinner",
      webName = "spinner",
      componentsToReplace = setOf("androidx.compose.material.CircularProgressIndicator")
    ),
    ;

    val url: String = "https://backpack.github.io/components/$webName"

    private fun replacesComponent(componentToReplace: String) = componentsToReplace.contains(componentToReplace)

    companion object {
      internal fun find(componentToReplace: String) =
        values().firstOrNull { it.replacesComponent(componentToReplace) }
    }
  }
}
