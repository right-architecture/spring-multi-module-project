package test.commerce.front.api

data class Email(val localPart: String) {
    override fun toString(): String {
        return "$localPart@bruno.com"
    }
}
