package com.mrecun.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mrecun.R
import com.mrecun.adapter.RecyclerAdapter
import com.mrecun.databinding.FragmentPageBinding
import com.mrecun.model.PageModel
import com.mrecun.model.State
import com.mrecun.ui.viewModels.PageFragmentViewModel


/**
 * A simple [BaseFragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PageFragment : BaseFragment<PageFragmentViewModel>() {

    private var shouldLoadData = false

    private val adapter = RecyclerAdapter()

    private var pageModel: PageModel? = null

    lateinit var binding: FragmentPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pageModel = it.getParcelable(ARG_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setLayout(inflater,R.layout.fragment_page,container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToView()
        initRecycler()
    }

    fun loadData() {
        shouldLoadData = if (isViewModelInitialized()) {
            viewModel.updatePageData(pageModel!!)
            false
        } else {
            true
        }
    }

    private fun subscribeToView() {
        if (shouldLoadData) {
            loadData()
        }
        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.network.observe(this, Observer {
            when (it.state) {
                State.LOADED -> {
                    binding.loading = false
                    binding.errorMessage = ""
                }
                State.RUNNING -> {
                    binding.loading = true
                    binding.errorMessage = ""
                }
                else -> {
                    binding.loading = false
                    binding.errorMessage = it.msg
                }
            }
        })
    }



    private fun initRecycler(){
        binding.recyclerView.layoutManager = GridLayoutManager(activity!!, 2)
        binding.recyclerView.adapter = adapter
    }

    override fun getFactory(): ViewModelProvider.Factory = PageFragmentViewModel.Factory(executor,service)

    override fun getViewModelClass(): Class<PageFragmentViewModel> = PageFragmentViewModel::class.java

    companion object {

        const val ARG_PAGE = "pageModel"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param pageModel Parameter 1.
         * @return A new instance of fragment PageFragment.
         */
        @JvmStatic
        fun newInstance(pageModel: PageModel) = PageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PAGE, pageModel)
                }
            }
    }
}
