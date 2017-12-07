package apps.steve.fire.randomchat.base.navigation

/**
 * Created by @stevecampos on 7/12/2017.
 */
data class NavigationState constructor(
        var activeTag: String? = null,
        var firstTag: String? = null,
        var isCustomAnimationUsed: Boolean = false) {

    fun clear() {
        activeTag = null
        firstTag = null
    }
}