package com.example.valorpay.view.fragments

import UserAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.valorpay.R
import com.example.valorpay.databinding.FragmentHomeBinding
import com.example.valorpay.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), UserAdapter.ItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UserAdapter
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=FragmentHomeBinding.inflate(inflater)
        val root: View = binding.root
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        initViewModel()
        binding.userRecyclerView.apply {
            this.layoutManager=LinearLayoutManager(activity)
        }


        return root
    }
    private fun initViewModel() {


        viewModel.loadData()

        observeData()

    }

    private fun observeData() {

        viewModel.getLiveData().observe(requireActivity()) {

            binding.progressBar.visibility = View.INVISIBLE

            if (it != null) {
                if (it.isEmpty()) {
                    binding.userRecyclerView.visibility = View.INVISIBLE
                } else {
                    binding.userRecyclerView.visibility = View.VISIBLE
                    adapter = UserAdapter( it,requireContext(),this)
                    binding.userRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            } else {
                binding.userRecyclerView.visibility = View.INVISIBLE
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

    override fun onItemClick(position: Int) {

        val bundle=Bundle()
        bundle.putString("id", viewModel.getLiveData().value!![position].id.toString())
        findNavController().navigate(R.id.action_navigation_home_to_userDetail,bundle)

    }

}