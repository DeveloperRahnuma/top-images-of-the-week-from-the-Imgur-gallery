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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // for use debug things under this activity
    private val TAG = "MainActivity_Debug"

    // for bind the view with this activity
    private lateinit var binding: ActivityMainBinding

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

        //for call server api and fetch the data
        getAlbumDetailFromServer("")

        //function for implementing animated search bar on toolbar
        searchSetup()

    }

    //this function is for calling API and collecting the data
    //we used flow which emit different type of data sequentially
    // once you receive the data perform action according to that
    private fun getAlbumDetailFromServer(searchImageName: String){
        viewModel?.getImage(
            searchImageName = searchImageName,
            fetchedAlbumData = {
                hideNoData()
                hideloading()
                setUpRecycleView(it)
            },
            showLoading = {
                hideNoData()
                showloading()
            },
            showNoData = {
                hideloading()
                showNoData()
            }
        )
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

    // loading screen hide and actual data will show on screen
    // runOnUiThread will execute this code on main thread because other thread cannot touch the view
    private fun hideloading() {
        runOnUiThread {
            binding.progressCircularContainer.visibility = View.INVISIBLE
        }
    }


    // the loading screen will shown on screen basically it show the data start loading
    // runOnUiThread will execute this code on main thread because other thread cannot touch the view
    private fun showloading() {
        runOnUiThread {
            binding.progressCircularContainer.visibility = View.VISIBLE
        }
    }


    // the no data screen will shown on screen basically it show the server have no image
    // with the query you search
    // runOnUiThread will execute this code on main thread because other thread cannot touch the view
    private fun showNoData() {
        runOnUiThread {
            binding.noData.visibility = View.VISIBLE
        }
    }


    //  the no data screen will hide on screen, means server have data return
    // runOnUiThread will execute this code on main thread because other thread cannot touch the view
    private fun hideNoData() {
        runOnUiThread {
            binding.noData.visibility = View.INVISIBLE
        }
    }



    // this function will setup the animated search bar on toolbar
    // its will provide the lister that will give three callback
    private fun searchSetup(){
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {

            //onQueryTextSubmit -  will triggered when user click on search button on keyboard
            override fun onQueryTextSubmit(query: String): Boolean {
                getAlbumDetailFromServer(query)
                Log.d("SimpleSearchView", "Submit:$query")
                return false
            }

            //onQueryTextChange -  will triggered when user either enter or click any letter
            override fun onQueryTextChange(newText: String): Boolean {
                // Add this code to set threshold to expected value
                return false
            }

            //onQueryTextCleared -  will triggered when user completely clear query
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
            R.id.view_change -> {
                changeListViewDesignAndIcon(item)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    //when gird or list icon get click
    // then icon of that item and recycle view layout manager get changed
    private fun changeListViewDesignAndIcon(item: MenuItem){
        if(item.title.equals("Grid View")){
            //change item icon to list view and recycle design will be grid view
            toggalIconChangeToList(toggleIcon = item)
            binding.recycleView.layoutManager = GridLayoutManager(this,2)
        }else{
            //change item icon to grid view and recycle design will be list view
            toggalIconChangeToGrid(toggleIcon = item)
            binding.recycleView.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun toggalIconChangeToGrid(toggleIcon : MenuItem){
        toggleIcon.title = "Grid View"
        toggleIcon.icon = getDrawable(R.drawable.gridviewicon)
    }


    private fun toggalIconChangeToList(toggleIcon : MenuItem){
        toggleIcon.title = "List View"
        toggleIcon.icon = getDrawable(R.drawable.listview)
    }


    override fun onBackPressed() {
        if (binding.searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }
}