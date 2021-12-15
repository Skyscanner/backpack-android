# Bar Chart

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

E.g. The Bar Chart component can be used in both XML and Kotlin/Java,
however, feeding the component with data can only be done programmatically.

Example of a Bar Chart in XML

```xml
<net.skyscanner.backpack.barchart.BpkBarChart
    android:id="@+id/bar_chart"
    android:layout_width="match_parent"
    android:layout_height="240dp" />
```

Example of a Bar Chart in Kotlin

```Kotlin
import net.skyscanner.backpack.barchart.BpkBarChart

BpkBarChart(context).apply {
    model = //.. initialisation
}
```

To render data you need to set a view model to `BarChart` in code.
Detailed documentation on each property of the model is available [here](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.barchart).

Here's some basic example in Kotlin:

```kotlin

fun createGroup() = BpkBarChart.Group(
  title = "Group Title",
  items = mutableListOf<BpkBarChart.Column>().apply {
    for (i in 0 until 10) {
      add(BpkBarChart.Column(
        title = "Bar Title",
        subtitle = "Bar Subtitle",
        badge = "Bar Badge",
        value = 0.5f,
        inactive = false
      ))
    }
  }
)

barChart.model = BpkBarChart.Model(
  groups = listOf(
    createGroup(),
    createGroup()
  ),
  legend = BpkBarChart.Legend(
    "£",
     "No Price"
   )
 )
```

Another example can be found in `BarChartStory` in the sample project.

## Theme Props

All colours support `ColorStateList`.

- `barChartColumnTitleColor` - `state_selected` will be used when the bar is selected.
- `barChartColumnSubtitleColor` - `state_selected` will be used when the bar is selected.
- `barChartGroupTitleColor`
- `barChartBarBackgroundColor` - `state_activated` this can be used to split the colours of active/inactive sets. `state_selected` will be used when the bar is selected.
- `barChartBarForegroundColor` - `state_activated` this can be used to split the colours of active/inactive sets. `state_selected` will be used when the bar is selected.
- `barChartLineColor`
- `barChartPopupBackgroundColor`
- `barChartPopupTextColor`

Styles can be changed globally through `bpkBarChartStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
