package com.annas.githubuser.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.annas.githubuser.R
import com.annas.githubuser.core.ui.UserAdapter
import com.annas.githubuser.favorite.databinding.FragmentFavoriteBinding
import com.annas.githubuser.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : androidx.fragment.app.Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val USERNAME = "USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val userAdapter = UserAdapter()
            userAdapter.onItemClick = { selectedData ->
                val bundle = Bundle()
                bundle.putParcelable(USERNAME, selectedData)
                view.findNavController().navigate(R.id.action_navigation_favorite_to_navigation_detail, bundle)
            }

            favoriteViewModel.favoriteUsers.observe(viewLifecycleOwner) { favorites ->
                userAdapter.setData(favorites)
                binding.viewEmpty.visibility =
                    if (favorites.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}