package com.example.newsapp_c2.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_c2.NewsAdapter
import com.example.newsapp_c2.R
import com.example.newsapp_c2.databinding.FragmentNewsBinding
import com.example.newsapp_c2.model.Category
import com.example.newsapp_c2.model.SourcesItem
import com.google.android.material.tabs.TabLayout

class NewsFragment(val category: Category) : Fragment() {

    lateinit var dataBinding :FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_news,container,false)
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)
        return dataBinding.root

    }


    lateinit var viewModel : NewsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeToLiveData()
        viewModel.getSources(category)
    }

   fun subscribeToLiveData(){
       viewModel.sourceLiveData.observe(viewLifecycleOwner, Observer {source->
           // do anything in UI
           addSourcesToTabLayout(source)
       })

       viewModel.newLiveData.observe(viewLifecycleOwner, Observer {news->
           // put news in rV
           adapter.changeItem(news)
       })

       viewModel.progressBarVisible.observe(viewLifecycleOwner, Observer {
           dataBinding.progressBar.isVisible = it
       })
   }

    val adapter = NewsAdapter(null)
    fun initView(){

        dataBinding.recyclerView.adapter =adapter
    }



    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {

        sources?.forEach {source->

            val tab = dataBinding.tabLayout.newTab()

            tab.setText(source?.name)
            tab.tag = source
            dataBinding.tabLayout.addTab(tab)

            dataBinding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

                override fun onTabSelected(tab: TabLayout.Tab?) {

                    val source = tab?.tag as SourcesItem
                    viewModel.getNewsBySources(source)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    viewModel.getNewsBySources(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.e("tab",tab?.text.toString())
                }

            })
        }
        dataBinding.tabLayout.getTabAt(0)?.select()
    }


}