package com.example.newsapp_c2.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_c2.R
import com.example.newsapp_c2.model.Category

class CategoryFragment :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category,container,false)
    }
    val categoryList = listOf(
        Category("sports","sports",R.drawable.ic_sports,R.color.red_light),
        Category("technology","technology",R.drawable.ic_politics,R.color.blue),
        Category("health","health",R.drawable.ic_health,R.color.pink),
        Category("business","business",R.drawable.ic_bussines,R.color.brown),
        Category("general","general",R.drawable.ic_environment,R.color.blue_light),
        Category("science","science",R.drawable.ic_science,R.color.yellow),
    )

    lateinit var recyclerView: RecyclerView
    val adapter = CategoriesAdapter(categoryList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        recyclerView = requireView().findViewById(R.id.recycler_view_category)

        recyclerView.adapter = adapter
    }

}