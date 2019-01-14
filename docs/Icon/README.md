# Icon

## Installation

Backpack Android is available through Jitpack. Check the main Readme for a complete installation guide.

## Usage

Icons are converted to image vectors and are available as drawables.

They can be used in code and XML.

Exampe usage in XML

```
<ImageView
android:layout_width="16dp"
  android:tint="@color/bpkGray900"
  android:layout_margin="@dimen/bpkSpacingSm"
  app:srcCompat="@drawable/bpk_flight"
  android:layout_height="16dp" />
````

Example usage in Kotlin

```
ImageView(context).apply {
  setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.bpk_flag))
}`
