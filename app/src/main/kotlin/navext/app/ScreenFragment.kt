package navext.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_screen.*
import navext.fragment.styled.styledNavArg
import navext.fragment.styled.styledNavNextId

class ScreenFragment : Fragment() {

    private val nextId by styledNavNextId()
    private val argumentTitle by styledNavArg(R.attr.title) { getString() }
    private val argumentDescription by styledNavArg(R.attr.description) { getString() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.text = argumentTitle
        description.text = argumentDescription

        next.isEnabled = nextId != null
        next.setOnClickListener {
            val nextId = nextId ?: return@setOnClickListener
            findNavController().navigate(nextId)
        }
    }
}