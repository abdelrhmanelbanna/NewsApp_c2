package com.example.newsapp_c2.ui.news

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp_c2.Constants
import com.example.newsapp_c2.api.ApiManager
import com.example.newsapp_c2.model.ArticlesItem
import com.example.newsapp_c2.model.Category
import com.example.newsapp_c2.model.NewsResponse
import com.example.newsapp_c2.model.SourcesItem
import com.example.newsapp_c2.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsVM :ViewModel() {

    val sourceLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressBarVisible =MutableLiveData<Boolean>()

    public fun getSources(category: Category) {
       // progressBar.isVisible = true
        progressBarVisible.value = true ;

        ApiManager.getApi().getSources(Constants.ApiKey,category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
               //     progressBar.isVisible = false
                    progressBarVisible.value = false ;
                    Log.e("error",t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    // add the response  to TabLayout
                 //   progressBar.isVisible = false
                    progressBarVisible.value = false ;
                   // addSourcesToTabLayout(response.body()?.sources)

                    sourceLiveData.value = response.body()?.sources
                }
            })
    }


    fun getNewsBySources(source: SourcesItem) {

      //  progressBar.isVisible = true
        progressBarVisible.value = true ;
        ApiManager.getApi().getNews(Constants.ApiKey,source.id?:"")
            .enqueue(object : Callback<NewsResponse> {

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                //    progressBar.isVisible = false
                    progressBarVisible.value = false
                    Log.e("error",t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    // show it in RV
                //    progressBar.isVisible = false
                    progressBarVisible.value = false
                   // adapter.changeItem(response.body()?.articles)
                    newLiveData.value = response.body()?.articles
                }
            })

    }

}