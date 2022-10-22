package com.annas.githubuser.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.annas.githubuser.MainActivity
import com.annas.githubuser.R
import com.annas.githubuser.core.data.Resource
import com.annas.githubuser.core.ui.UserAdapter
import com.annas.githubuser.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModel()
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter

    companion object {
        const val USERNAME = "USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            userAdapter = UserAdapter()
            userAdapter.onItemClick = { selectedData ->
                val bundle = Bundle()
                bundle.putParcelable(USERNAME, selectedData)
                view.findNavController()
                    .navigate(R.id.action_navigation_home_to_navigation_detail, bundle)
            }

            homeViewModel.users.observe(viewLifecycleOwner) { users ->
                if (users != null) {
                    Log.i("Users", "Not Null")
                    when (users) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.viewError.root.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.GONE
                            userAdapter.setData(users.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = "Oops...Something went wrong!"
                        }
                    }
                }
            }

            with(binding.rvUser) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = userAdapter
                Log.i("RecyclerView", "Attached")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        val searchView =
            ((context as MainActivity).supportActionBar?.themedContext ?: context)?.let {
                SearchView(
                    it
                )
            }
        menu.findItem(R.id.menuSearch).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView!!.queryHint = resources.getString(R.string.find_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.searchQuery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeViewModel.searchQuery(newText)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}