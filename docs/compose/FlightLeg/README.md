# FlightLeg

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.flightleg)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/flightleg)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FlightLeg/screenshots/default.png" alt="FlightLeg component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FlightLeg/screenshots/default_dm.png" alt="FlightLeg component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a FlightLeg:

```Kotlin
import net.skyscanner.backpack.compose.flightleg.BpkFlightLeg

BpkFlightLeg(
  departureArrivalTime = "19:50 - 22:45",
  description = buildAnnotatedString {
    withStyle(
      SpanStyle(
        background = BpkTheme.colors.statusDangerFill,
        color = BpkTheme.colors.textOnLight,
      ),
    ) {
      append("LHR")
    }
    append("-SIN, SwissAir")
  },
  stopsInfo = "2 Stops",
  highlightStopsInfo = true,
  duration = "7h 55m",
  nextDayArrival = "+1",
  operatedBy = "Operated by Ryanair",
  warning = "Change airports in London",
  carrierLogoContent = {
    Box(
      modifier = Modifier.padding(top = BpkSpacing.Sm),
    ) {
      BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
    }
  },
)
```
