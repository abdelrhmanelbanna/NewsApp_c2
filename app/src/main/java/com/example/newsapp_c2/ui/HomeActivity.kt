package com.example.newsapp_c2.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.newsapp_c2.R
import com.example.newsapp_c2.model.Category
import com.example.newsapp_c2.ui.category.CategoryFragment
import com.example.newsapp_c2.ui.setting.SettingFragment

class HomeActivity : AppCompatActivity() {
    
    lateinit var menuIcon:ImageView
    lateinit var drawerLayout: DrawerLayout

    lateinit var categoryTv : TextView
    lateinit var settingTv:TextView

    val categoryFragment = CategoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setContentView(R.layout.activity_home)

        initView()

    }

    private fun initView() {
        menuIcon = findViewById(R.id.ic_menu)
        drawerLayout = findViewById(R.id.drawer_layout)
        categoryTv = findViewById(R.id.category_tv)
        settingTv = findViewById(R.id.setting_tv)

        menuIcon.setOnClickListener{
            drawerLayout.open()
        }
        categoryTv.setOnClickListener{
            // push category
            pushFragment(CategoryFragment())
            drawerLayout.close()
        }

        settingTv.setOnClickListener {
            pushFragment(SettingFragment())
            drawerLayout.close()
        }

        pushFragment(categoryFragment)

        categoryFragment.onCategoryClickLisytner = object : CategoryFragment.OnCategoryClickLisytner{
            override fun onCategoryClick(category: Category, pos: Int) {
                pushFragment(NewsFragment(category),true)
            }
        }

    }

    fun pushFragment(fragment:Fragment,addToBackStack:Boolean=false){
      val push =  supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)

            if (addToBackStack){
             push.addToBackStack("name")
            }

            push.commit()

    }


}