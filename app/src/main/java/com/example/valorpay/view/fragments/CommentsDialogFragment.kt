package com.example.valorpay.view.fragments

import com.example.valorpay.view.adapter.CommentsAdapter
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.valorpay.databinding.FragmentCommentsDialogListDialogBinding
import com.example.valorpay.viewmodel.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCommentsDialogListDialogBinding? = null
    private lateinit var adapter: CommentsAdapter
    private lateinit var viewModel: CommentsViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentsDialogListDialogBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CommentsViewModel::class.java]

        initViewModel()
        binding.rvComments.apply {
            this.layoutManager= LinearLayoutManager(activity)
        }
        binding.iconRefresh.setOnClickListener {
            viewModel.getLiveData().value?.clear()
            viewModel.loadComments(requireArguments().getString("id").toString())
        }
        return binding.root
    }
    private fun initViewModel() {
        viewModel.loadComments(requireArguments().getString("id").toString())
        observeData()
    }

    private fun observeData() {
        viewModel.getLiveData().observe(requireActivity()) {
            if (it != null) {
                if (it.isEmpty()) {
                    binding.rvComments.visibility = View.INVISIBLE
                } else {
                    binding.rvComments.visibility = View.VISIBLE
                    adapter = CommentsAdapter( it,requireContext())
                    binding.rvComments.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            } else {
                binding.rvComments.visibility = View.INVISIBLE
            }
        }

        this.viewModel.mShowProgressBar.observeForever {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.mShowApiError.observeForever {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}