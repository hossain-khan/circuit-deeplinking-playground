package app.example.deeplinking

import android.net.Uri
import androidx.core.net.toUri

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
internal const val DEEP_LINK_PATH_INBOX = "inbox"
internal const val DEEP_LINK_PATH_VIEW_EMAIL = "view_email"
internal const val DEEP_LINK_PATH_DRAFT_NEW_EMAIL = "new_email"
internal const val DEEP_LINK_EMAIL_ID_QUERY_PARAM = "emailId"

/**
 * Creates a deep link URI for the email.
 *
 * For example: `circuitapp://emailonthego/view_email?emailId=2`
 */
internal fun createViewEmailDeeplinkUri(emailId: String): Uri =
    "$DEEP_LINK_SCHEME://$DEEP_LINK_HOST/$DEEP_LINK_PATH_VIEW_EMAIL/?$DEEP_LINK_EMAIL_ID_QUERY_PARAM=$emailId".toUri()
