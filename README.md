# NavigationExtensions
navex is extension for AndroidX Navigation.

**This repository is not published to Maven.** It may be released if there are many requests.

## Summary
navex provides some extensions

|package|description|tested|
|:--|:--|:--|
|navext-activity-request|launching activity navigator, call Activity.startActivityForResult||
|navext-activity-result|pop activity navigator, call Activity.setResult & Activity.finish||
|navext-fragment-styled|navigation graph based style navigator, extension for FragmentNavigator|:white_check_mark:|
|navext-fragment|hosting some navigator Fragment||

## Usage
### navext-fragment-styled
FragmentStyledNavigatior is extension for define different resource and transition on navigation graph.

1. add attributes, res/values
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="Screen">
        <attr format="string" name="title"/>
        <attr format="string" name="description"/>
    </declare-styleable>
</resources>
```

2. add style, res/values
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="Screen">
        <item name="title"/>
        <item name="description"/>
    </style>

    <style name="Screen.Route1.First" parent="Screen">
        <item name="title">Route1 First</item>
        <item name="description">Rout1 First Description</item>
    </style>

    <style name="Screen.Route1.Second" parent="Screen">
        <item name="title">Route1 Second</item>
        <item name="description">Rout1 Second Description</item>
    </style>
</resources>
```

3. add fragment  
`styledNavNextId()` and `styledNavArg()` value is injected on NavigationGraph
```kotlin
class ScreenFragment : Fragment() {

    private val nextId by styledNavNextId()
    private val argumentTitle by styledNavArg(R.attr.title) { getString() }
    private val argumentDescription by styledNavArg(R.attr.description) { getString() }
}
```

4. add navigation graph, res/navigation
```xml

<?xml version="1.0" encoding="utf-8"?>
<navigation android:id="@+id/navigation_main"
            app:startDestination="@id/startFragment"
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto">

    <fragment
        android:id="@+id/startFragment"
        android:name="navext.app.StartFragment">
        <action
            android:id="@+id/transitionToRoute1First"
            app:destination="@+id/route1First"/>
        <action
            android:id="@+id/transitionToRoute2First"
            app:destination="@id/route2First"/>
    </fragment>

    <fragment-styled
        android:id="@+id/route1First"
        android:name="navext.app.ScreenFragment"
        app:nextId="@id/transitionToRoute1Second"
        app:style="@style/Screen.Route1.First">
        <action
            android:id="@+id/transitionToRoute1Second"
            app:destination="@+id/route1Second"/>
    </fragment-styled>

    <fragment-styled
        android:id="@+id/route1Second"
        android:name="navext.app.ScreenFragment"
        app:nextId="@id/transitionToRoute1Third"
        app:style="@style/Screen.Route1.Second">
        <action
            android:id="@+id/transitionToRoute1Third"
            app:destination="@id/route1Third"/>
    </fragment-styled>
  
    <!-- etc... -->
</navigation>
```

## License
This library is under MIT License

### Extension
- using [Kotlin Standard Library](https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib), published by [Apache License 2.0](https://github.com/JetBrains/kotlin/tree/master/license)
- using [AndroidX](https://github.com/aosp-mirror/platform_frameworks_support), published by [Apache License 2.0](https://github.com/aosp-mirror/platform_frameworks_support/blob/androidx-master-dev/LICENSE.txt)
- using [AndroidX Test](https://github.com/android/android-test), published by [Apache License 2.0](https://github.com/android/android-test/blob/master/LICENSE)
- using [JUnit4](https://github.com/junit-team/junit4), published by [Eclipse Public License 1.0](https://github.com/junit-team/junit4/blob/master/LICENSE-junit.txt)

### Sample
- using [Kotlin Standard Library](https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib), published by [Apache License 2.0](https://github.com/JetBrains/kotlin/tree/master/license)
- using [AndroidX](https://github.com/aosp-mirror/platform_frameworks_support), published by [Apache License 2.0](https://github.com/aosp-mirror/platform_frameworks_support/blob/androidx-master-dev/LICENSE.txt)
- using [AndroidX Test](https://github.com/android/android-test), published by [Apache License 2.0](https://github.com/android/android-test/blob/master/LICENSE)
- using [JUnit4](https://github.com/junit-team/junit4), published by [Eclipse Public License 1.0](https://github.com/junit-team/junit4/blob/master/LICENSE-junit.txt)

## Other
Author: [@MeilCli](https://github.com/MeilCli)
