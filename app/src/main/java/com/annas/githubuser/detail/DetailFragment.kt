package com.annas.githubuser.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.annas.githubuser.R
import com.annas.githubuser.core.data.Resource
import com.annas.githubuser.core.domain.model.User
import com.annas.githubuser.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    companion object {
        const val USERNAME = "USERNAME"
    }

    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        user = arguments?.getParcelable(USERNAME)!!
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            user.username + "'s Detail"
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0F

        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.imgAvatar)
        binding.txtUsername.text = user.username

        detailViewModel.isFavorite(user.username).observe(viewLifecycleOwner) { isFavorite ->
            binding.fab.setOnClickListener {
                detailViewModel.setFavorite(user, isFavorite)
            }
            setStatusFavorite(isFavorite)
        }

        detailViewModel.getUserDetail(user.username).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Log.i("User", user.data.toString())
                when (user) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.cardDetail.visibility = View.VISIBLE
                        binding.lnrDetail.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.txtName.text = user.data!!.name
                        binding.txtLocation.text =
                            if (user.data!!.location != null) user.data!!.location else "No Information"
                        binding.txtWorking.text =
                            if (user.data!!.company != null) user.data!!.company else "No Information"
                        binding.tvFollowers.text = user.data!!.followers.toString()
                        binding.tvFollowing.text = user.data!!.following.toString()
                        binding.tvRepository.text = user.data!!.publicRepos.toString()

                        binding.progressBar.visibility = View.GONE
                        binding.cardDetail.visibility = View.VISIBLE
                        binding.lnrDetail.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.cardDetail.visibility = View.GONE
                        binding.lnrDetail.visibility = View.GONE
                    }
                }
            }
        }
        return binding.root
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_favorite
                )
            })
        } else {
            binding.fab.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_favorite_border
                )
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}