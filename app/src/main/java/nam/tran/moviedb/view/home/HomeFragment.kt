package nam.tran.moviedb.view.home

import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentHomeBinding
import tran.nam.core.view.mvvm.BaseFragmentVM

class HomeFragment : BaseFragmentVM<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home
}