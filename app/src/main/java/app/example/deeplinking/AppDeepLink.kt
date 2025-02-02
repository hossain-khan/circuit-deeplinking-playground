package app.example.deeplinking

import android.net.Uri
import androidx.core.net.toUri

/**
 *
 * ```
 * $ adb shell am start -W -a android.intent.action.VIEW -d "appurischeme://view_email/2" app.example
 * ```
 */
internal const val DEEP_LINK_SCHEME = "appurischeme"
internal const val DEEP_LINK_HOST_VIEW_EMAIL = "view_email"

/**
 * Creates a deep link URI for the email.
 *
 * For example: `appurischeme://view_email/123`
 */
internal fun createViewEmailDeeplinkUri(emailId: String): Uri = "$DEEP_LINK_SCHEME://$DEEP_LINK_HOST_VIEW_EMAIL/$emailId".toUri()

/**
 * Extracts the email id from the deep link URI.
 *
 * For example:
 * Get the email id `123` from the path: `appurischeme://view_email/123`
 */
internal fun getIdFromPath(dataUri: Uri): String? = dataUri.pathSegments.firstOrNull()
