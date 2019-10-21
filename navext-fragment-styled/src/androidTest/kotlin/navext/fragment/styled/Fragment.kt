package navext.fragment.styled

import androidx.fragment.app.Fragment
import navext.fragment.styled.test.R

class FirstFragment : Fragment() {

    val nextId by styledNavNextId()
    val title by styledNavArg(R.attr.title) { getString() }
}

class SecondFragment : Fragment() {

    val nextId by styledNavNextId()
    val title by styledNavArg(R.attr.title) { getString() }
}

class ThirdFragment : Fragment() {

    val nextId by styledNavNextId()
    val title by styledNavArg(R.attr.title) { getString() }
}