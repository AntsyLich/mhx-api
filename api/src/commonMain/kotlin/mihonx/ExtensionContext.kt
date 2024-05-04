package mihonx

import io.ktor.client.HttpClient

class ExtensionContext(
    val httpClient: HttpClient,
    val preferenceStoreFactory: (ExtensionItem) -> Unit,
    val evaluateJsScript: (String) -> Any?,
)
