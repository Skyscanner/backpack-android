package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.appsearchmodal.AppSearchModalResult
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Search

private const val SkeletonCount = 10
private const val SkeletonItemWidth = 200

@Composable
internal fun BpkAppSearchModalImpl(
    inputText: String,
    inputHint: String,
    results: AppSearchModalResult,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (results) {
        is AppSearchModalResult.Error -> {
            BpkSearchModalError(results = results)
        }

        else -> {
            Column(modifier = modifier) {
                BpkTextField(
                    icon = BpkIcon.Search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BpkSpacing.Base),
                    value = inputText,
                    placeholder = inputHint,
                    onValueChange = onInputChanged,
                )
                LazyColumn {
                    if (results is AppSearchModalResult.Content) {
                        results.shortcuts?.let {
                            item {
                                BpkShortcuts(it)
                            }
                        }
                        items(results.sections) {
                            BpkSection(
                                section = it,
                                Modifier.padding(BpkSpacing.Base),
                            )
                        }
                    } else if (results is AppSearchModalResult.Loading) {
                        items(SkeletonCount) {
                            BpkBodyTextSkeleton(
                                modifier = Modifier
                                    .padding(BpkSpacing.Base)
                                    .width(SkeletonItemWidth.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
