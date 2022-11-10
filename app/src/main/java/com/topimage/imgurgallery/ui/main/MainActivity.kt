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

    //view model instance it should be provided by hilt but hilt current version
    // have some issue so
    // viewModel: MainViewModel? by viewmodel()
    // not working
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityMainBinding generated from xml file name
        //that's bind with this activity to display content here
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //we are using toolbar in place of action bar ( for it you need to change them of app also )
        setSupportActionBar(binding.toolbar)

        //getting instance of view mode here and why we are creating instance like this
        //explained up side where viewModel declared
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //for make loading screen visible or hide both
        isShowLoadingScreen(needToShow = false)

        //for call server api and fetch the data
        getImage("")

        //function for implementing animated search bar on toolbar
        searchSetup()

    }

    //this function is for calling API and collecting the data
    //we used flow which emit different type of data sequentially
    // once you recive the data perform action according to that
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


    // set up the RecyclerView and its layout manager
    // layout manager can changed later too
    private fun setUpRecycleView(imageInfoList: List<AlbumResponce>){
        runOnUiThread{
            binding.recycleView.layoutManager = LinearLayoutManager(this)
            val adapter = MainRecycleAdapter(this@MainActivity,imageInfoList)
            binding.recycleView.adapter = adapter
        }
    }

    // function for both work if needToShow will be true
    // then loading screen will shown on screen basically it show the data start loading
    // and if needToShow will false then loading screen hide and actual data will
    // displayed on screen
    private fun isShowLoadingScreen(needToShow : Boolean){
        runOnUiThread {
            if(needToShow){
                binding.progressCircularContainer.visibility = View.VISIBLE
            }else{
                binding.progressCircularContainer.visibility = View.INVISIBLE
            }
        }
    }


    // this function will setup the animated search bar on toolbar
    // its will provide the lister that will give three callback
    //onQueryTextSubmit, onQueryTextChange, onQueryTextCleared
    //onQueryTextSubmit -  will triggered when user click on search button on keyboard
    //onQueryTextChange -  will triggered when user either enter or click any letter
    //onQueryTextCleared -  will triggered when user completely clear query
    fun searchSetup(){
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getImage(query)
                Log.d("SimpleSearchView", "Submit:$query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Add this code to set threshold to expected value
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("SimpleSearchView", "Text cleared")
                return false
            }
        })

    }

    // adding optional menu which is on toolbar in a from of icon
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val item: MenuItem = menu!!.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            //when search icon get clicked
            R.id.action_search -> {
                binding.searchView.showSearch(animate = true)
                return true
            }
            //when gird or list icon get click
            // then icon of that item and recycle view layout manager get changed
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