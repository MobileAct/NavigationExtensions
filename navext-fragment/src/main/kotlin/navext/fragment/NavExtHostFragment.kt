package navext.fragment

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import navext.activity.request.ActivityRequestNavigator
import navext.activity.result.ActivityResultNavigator
import navext.fragment.styled.FragmentStyledNavigator

class NavExtHostFragment : NavHostFragment() {

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)

        navController.navigatorProvider += ActivityRequestNavigator(requireActivity())
        navController.navigatorProvider += ActivityResultNavigator(requireActivity())
        navController.navigatorProvider += FragmentStyledNavigator(navController.navigatorProvider)
    }
}