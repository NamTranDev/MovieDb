package nam.tran.moviedb.view.home

import tran.nam.state.State

interface INetworkState {
    fun setNetworkState(newNetworkState: State?)
}