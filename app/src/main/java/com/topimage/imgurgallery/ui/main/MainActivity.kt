package com.topimage.imgurgallery.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import com.topimage.imgurgallery.R
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.databinding.ActivityMainBinding
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // for use debug things under this activity
    val TAG = "MainActivity_Debug"
    // for bind the view with this activity
    lateinit var binding: ActivityMainBinding
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityMainBinding generated from xml file name
        //that's bind with this activity to display content here
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        isShowLoadingScreen(needToShow = false)
        getImage("")
        searchSetup()

    }

    private fun getImage(searchImage : String){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.getWeekTopImage(searchImage)?.collect{
                when(it){
                    is Resource.Success -> {
                        isShowLoadingScreen(needToShow = false)
                        it.Data?.let { it1 -> setUpRecycleView(it1.data) }
                    }
                    is Resource.Loading -> {
                        isShowLoadingScreen(needToShow = true)
                        Log.i(TAG, "Loading State")
                    }
                    is Resource.Error -> {
                        isShowLoadingScreen(needToShow = false)
                        Log.i(TAG, "Loading State")
                    }
                    else -> {
                        Log.i(TAG, "Loading Errror state")
                    }
                }
            }
        }
    }


    // set up the RecyclerView
    private fun setUpRecycleView(imageInfoList: List<AlbumResponce>){
        runOnUiThread{
            binding.recycleView.layoutManager = LinearLayoutManager(this)
            val adapter = MainRecycleAdapter(this@MainActivity,imageInfoList)
            binding.recycleView.adapter = adapter
        }
    }

    private fun isShowLoadingScreen(needToShow : Boolean){
        runOnUiThread {
            if(needToShow){
                binding.progressCircularContainer.visibility = View.VISIBLE
            }else{
                binding.progressCircularContainer.visibility = View.INVISIBLE
            }
        }
    }

    fun searchSetup(){
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getImage(query)
                Log.d("SimpleSearchView", "Submit:$query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Add this code to set threshold to expected value
                lifecycleScope.launch {

                }
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("SimpleSearchView", "Text cleared")
                return false
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val item: MenuItem = menu!!.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_search -> {
                binding.searchView.showSearch(animate = true)
                return true
            }
            R.id.view_change -> {
                if(item.title.equals("Grid View")){
                    item.title = "List View"
                    item.icon = this.getDrawable(R.drawable.listview)
                    binding.recycleView.layoutManager = GridLayoutManager(this,2)
                }else{
                    item.title = "Grid View"
                    item.icon = this.getDrawable(R.drawable.gridviewicon)
                    binding.recycleView.layoutManager = LinearLayoutManager(this)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (binding.searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }
}