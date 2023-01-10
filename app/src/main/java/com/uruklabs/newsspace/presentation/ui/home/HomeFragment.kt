package com.uruklabs.newsspace.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.uruklabs.newsspace.R
import com.uruklabs.newsspace.core.State
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.databinding.HomeFragmentBinding
import com.uruklabs.newsspace.presentation.adapter.PostListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainMenu()

        initSearch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initSnackbar()
        initRecyclerView()
        initBottomBar()
        initQuerySearchHintObserver()
    }

    private fun initMainMenu() {
        with(binding.homeToolbar) {
            this.inflateMenu(R.menu.search_menu)
        }
    }

    private fun initBottomBar() {
        with(binding.bottomNavigationView) {
            this.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_get_articles ->
                        viewModel.fethLatest(SpaceFlightNewsCategory.ARTICLES)
                    R.id.action_get_blogs -> viewModel.fethLatest(SpaceFlightNewsCategory.BLOGS)
                    R.id.action_get_reports -> viewModel.fethLatest(SpaceFlightNewsCategory.REPORTS)
                }
                true
            }
        }
    }

    private fun initQuerySearchHintObserver() {
        viewModel.category.observe(viewLifecycleOwner) {
            searchView.queryHint = getString(R.string.search_in_hint) + " " + when (it) {
                SpaceFlightNewsCategory.ARTICLES -> getString(R.string.articles)
                SpaceFlightNewsCategory.BLOGS -> getString(R.string.blogs)
                SpaceFlightNewsCategory.REPORTS -> getString(R.string.reports)
            }
        }
    }

    private fun initSearch() {
        with(binding.homeToolbar) {
            val searchItem = menu.findItem(R.id.action_search)
            searchView = searchItem.actionView as SearchView
            searchView.isIconified = false

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val searchString = searchView.query.toString()
                    viewModel.searchPostsByTile(searchString)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        viewModel.searchPostsByTile(it)
                    }
                    return true
                }
            })
        }
    }

    private fun initSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initRecyclerView() {

        val adapter = PostListAdapter {

            findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToReadPostFragment(it))
        }
        binding.homeRv.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(state.result)
                }
            }
        }
    }

    /**
     * Esse método faz a inicialização do DataBinding.
     * O arquivo XML possui uma variável viewModel, que precisa
     * ser vinculada ao ViewModel instanciado. Também é preciso
     * atribuir um LifeCycleOwner para que os bindings de live data
     * funcionem.
     */
    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
