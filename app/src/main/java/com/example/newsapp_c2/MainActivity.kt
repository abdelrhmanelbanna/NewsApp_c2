package com.example.newsapp_c2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_c2.api.ApiManager
import com.example.newsapp_c2.model.NewsResponse
import com.example.newsapp_c2.model.SourcesItem
import com.example.newsapp_c2.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout:TabLayout
    lateinit var progressBar:ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         initView()

        getSources()

    }

    val adapter = NewsAdapter(null)
    fun initView(){
        tabLayout = findViewById(R.id.tab_layout)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter =adapter
    }

    private fun getSources() {
        progressBar.isVisible = true

        ApiManager.getApi().getSources(Constants.ApiKey)
            .enqueue(object :Callback<SourcesResponse>{
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    Log.e("error",t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    // add the response  to TabLayout
                    progressBar.isVisible = false
                    addSourcesToTabLayout(response.body()?.sources)
                }
            })
    }

    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {

        sources?.forEach {source->

            val tab = tabLayout.newTab()

            tab.setText(source?.name)
            tab.tag = source
            tabLayout.addTab(tab)

            tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

                override fun onTabSelected(tab: TabLayout.Tab?) {

                    val source = tab?.tag as SourcesItem
                    getNewsBySources(source)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsBySources(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                   Log.e("tab",tab?.text.toString())
                }

            })
        }

        tabLayout.getTabAt(0)?.select()

    }

    private fun getNewsBySources(source: SourcesItem) {

        progressBar.isVisible = true
        ApiManager.getApi().getNews(Constants.ApiKey,source.id?:"")
            .enqueue(object :Callback<NewsResponse>{

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    Log.e("error",t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    // show it in RV
                    progressBar.isVisible = false
                    adapter.changeItem(response.body()?.articles)
                }
            })

    }
}