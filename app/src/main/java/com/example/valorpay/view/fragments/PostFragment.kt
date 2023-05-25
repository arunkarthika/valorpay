package com.example.valorpay.view.fragments

import com.example.valorpay.view.adapter.PostAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.valorpay.R
import com.example.valorpay.databinding.FragmentPostBinding
import com.example.valorpay.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(), PostAdapter.PostItemClickListener {

    private lateinit var binding: FragmentPostBinding
    private lateinit var adapter: PostAdapter
    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPostBinding.inflate(inflater)
        val root: View = binding.root
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]

        initViewModel()
        binding.rvPost.apply {
            this.layoutManager= LinearLayoutManager(activity)
        }
        return root
    }

    private fun initViewModel() {
        viewModel.loadPost(requireArguments().getString("id").toString())

        observeData()
    }

    private fun observeData() {
        viewModel.getLiveData().observe(requireActivity()) {
            if (it != null) {
                if (it.isEmpty()) {
                    binding.rvPost.visibility = View.INVISIBLE
                } else {
                    binding.rvPost.visibility = View.VISIBLE
                    adapter = PostAdapter( it,requireContext(),this)
                    binding.rvPost.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            } else {
                binding.rvPost.visibility = View.INVISIBLE
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
        findNavController().navigate(R.id.action_postFragment_to_commentsDialogFragment,bundle)

    }

}