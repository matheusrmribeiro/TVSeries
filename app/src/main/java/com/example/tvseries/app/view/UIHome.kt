package com.example.tvseries.app.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseries.R
import com.example.tvseries.app.adapters.ShowsAdapter
import com.example.tvseries.app.viewmodel.VMHome
import com.example.tvseries.databinding.FragmentUihomeBinding


class UIHome : Fragment() {

    lateinit var binding: FragmentUihomeBinding
    lateinit var vmHome: VMHome

    companion object {
        fun newInstance() = UIHome()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_uihome,
            container,
            false
        )
        return binding.root
    }

    private val adapter = ShowsAdapter(
        { show ->
            val action = UIHomeDirections.actionUIHomeToUIShowDetails(show)
            findNavController().navigate(action)
        },
        {
            vmHome.all(requireContext())
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmHome = ViewModelProvider(requireActivity()).get(VMHome::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = vmHome

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mnSearch -> {
                }
            }
            true
        }

        val searchView = binding.toolbar.menu.findItem(R.id.mnSearch).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                vmHome.search(requireContext(), query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        searchView.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(arg0: View) {
                adapter.clearItems()
                vmHome.all(requireContext())
                vmHome.setSearchMode(false)
            }

            override fun onViewAttachedToWindow(arg0: View) {
                vmHome.setSearchMode(true)
                searchView.setQuery("", false)
                vmHome.clearShows()
            }
        })

        binding.rvShows.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvShows.addItemDecoration(
            object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    outRect.left = 0
                    outRect.top = 0
                    outRect.right = 0
                    outRect.bottom = 0
                }
            }
        )
        binding.rvShows.adapter = adapter

        vmHome.shows.observe(viewLifecycleOwner, {
            if (it.isEmpty())
                adapter.clearItems()
            else
                adapter.addItems(it)
        })

        if (vmHome.shows.value?.isEmpty() == true)
            vmHome.all(requireContext())
    }

}