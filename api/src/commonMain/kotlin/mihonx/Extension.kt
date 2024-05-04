package mihonx

abstract class Extension(val context: ExtensionContext) {
    abstract fun getItems(): List<ExtensionItem>
}
