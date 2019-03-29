# Event listeners and state

## TL;DR

Adding default listeners and changing visual state inside our components should be done with caution and avoided when possible, as it can cause unexpected problems especially if consumers extend our components.

## Decision

When creating a component that changes its state based on user interaction (e.g. press/click) we should avoid adding listeners and changing the state inside the component whenever possible.

Preference should be given to exposing the state change logic via a function and letting users add the listeners as they see fit.

Consider the following example:

```Kotlin
// Bad
class BpkChip {
  init {
    setOnClickListener({
      if (!disabled) {
        selected = !selected
      }
    })
  }
}

// Good
class BpkChip {
  fun toggle() {
    if (!disabled) {
      selected = !selected
    }
  }
}

BpkChip().setClickListener { it.toggle() }
```

## Thinking

There are two main problems with adding the listener by default.

**Extension and order of initialization:**

Consider the example above again but now with a class extending our component and overriding the click listener. *Comments show the order of initialization.*

```Kotlin
class BpkChip {
  init {
    // 1
    setOnClickListener({
      if (!disabled) {
        selected = !selected
      }
    })
  }
}

class MyCustomChip {
  // 3
  val myAnalyticProp = AnalyticProp()
 
  override fun setOnClickListener(l: Listener) {
    // 2
    super.setOnClickListener(WrapWithAnalitics(l, myAnalyticProp))
  }
}

```

In the example above `setOnClickListener` will be called by the super class before the props and `init block` have been called in the child class. This can be very hard to identify, especially from a user perspective.

**Flexibility:**

Because with most listeners only one can be added (`setListener` vs `addListener`) if users need
to add a custom listener they will unintentionally override the behaviour we had defined for it. This can be avoided if we override the listener function and always add the behaviour, but this will make it impossible for users to disable this behaviour in certain occasions.

## Anything else

Documentation with examples should be provided to remind users that a listener should be added. 

In the occasions where a listener is absolutely required we should take precautions to avoid 
the two problems above, one course of action is a different event that users can register without affecting the default behaviour. For example, BpkChip could provide a `setOnSelectedStateChangeListener` so users can react to changes without replacing the click listener.
