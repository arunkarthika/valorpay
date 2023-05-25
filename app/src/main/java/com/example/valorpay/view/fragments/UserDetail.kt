package com.example.valorpay.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.valorpay.R
import com.example.valorpay.databinding.FragmentUserDetailBinding
import com.example.valorpay.util.AppConstant
import com.example.valorpay.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDetail : Fragment() {
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding= com.example.valorpay.databinding.FragmentUserDetailBinding.inflate(inflater)
        val root: View = binding.root
        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        initViewModel()

        binding.button.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id", viewModel.getUserData().value!!.id.toString())
            findNavController().navigate(R.id.action_userDetail_to_postFragment,bundle)
        }

        return root
    }


    private fun initViewModel() {
        viewModel.loadUserData(requireArguments().getString("id").toString())
        observeData()
    }

    private fun observeData() {
        viewModel.getUserData().observe(requireActivity()) {
            binding.progressBar.visibility = View.INVISIBLE
            if (it != null) {
                binding.name.text=it.name
                binding.email.text=it.email
                binding.tvMobile.text=it.phone
                binding.tvWebSite.text=it.website
                binding.tvCity.text=it.address.city
                binding.button.visibility=View.VISIBLE
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = it.name

                Glide.with(requireContext())
                    .load(AppConstant.SampleImageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(binding.userImage)

            }
        }
        viewModel.mShowApiError.observeForever {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.mShowProgressBar.observeForever {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

    }
}