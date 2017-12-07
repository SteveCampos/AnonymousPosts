package apps.steve.fire.randomchat.base.navigation

/**
 * Created by @stevecampos on 7/12/2017.
 */
@Experimental
sealed class BackStrategy {

    object KEEP : BackStrategy()
    object DESTROY : BackStrategy()
}