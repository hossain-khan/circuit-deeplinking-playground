package app.example.deeplinking

/**
 * URI scheme used in the app for deep-linking.
 *
 * Test out deep-linking from terminal:
 * ```
 * $ adb shell am start -W -a android.intent.action.VIEW -d "circuitapp://emailonthego/view_email?emailId=2" app.example
 *
 * # Deep link to inbox followed by email details
 * $ adb shell am start -W -a android.intent.action.VIEW -d "circuitapp://emailonthego/inbox/view_email/?emailId=2" app.example
 *
 * # Deep link to inbox, details, and draft (not a great example, but showcasing 3 screens)
 * $ adb shell am start -W -a android.intent.action.VIEW -d "circuitapp://emailonthego/inbox/view_email/new_email/?emailId=2" app.example
 * ```
 * For demo:
 * ```sh
 * adb shell am start -W \
 *   -a android.intent.action.VIEW \
 *   -d "circuitapp://emailonthego/inbox/view_email/new_email/?emailId=2"
 * ```
 */
internal const val DEEP_LINK_SCHEME = "circuitapp"
internal const val DEEP_LINK_HOST = "emailonthego"
