package com.popwoot.notesapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.popwoot.carouselbanner.Banner
import com.popwoot.notesapp.R
import com.popwoot.notesapp.adapter.NoteAdapter
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.mvvm.NoteViewModel
import com.popwoot.notesapp.ui.base.BaseActivity
import com.popwoot.notesapp.utils.CustomProgressDialog
import com.popwoot.notesapp.utils.ImageFactory
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NoteListActivity : BaseActivity() {

    private val pd by lazy { CustomProgressDialog(this, false) }
    private val mViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java) }
    private lateinit var mAdapter: NoteAdapter
    private val TAG = this::class.java.simpleName
    private var dateFilter = ""
    private var searchView: SearchView? = null
    private var itemsArray: List<NoteModel>  = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        observeLiveData()
    }


    private fun observeLiveData() {
        banner()
        pd.show()
        mViewModel.getAllNotes().observe(this, Observer {
            pd.hide()
            itemsArray=it
            notifyDataSetChanged()
        })
    }


    private fun banner(){
        val list = ArrayList<String>()
        Banner.init(ImageFactory())
        list.add("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2019/06/30/842662-katrinakaif-offbeat-films.jpg")
        list.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/201905/AKSHAY1.jpeg?HAKoXX_ba8nMjk4Vo.AOXOxLFBo7E6oY")
        list.add("https://en.janbharattimes.com/wp-content/uploads/2019/01/salman-khan-movies.jpg")
        list.add("https://static.asianetnews.com/images/01dax9bpf2hyymhqtbbst7p7tm/--15--1967------------_710x400xt.jpg")
        list.add("https://www.itl.cat/pngfile/big/86-867177_south-indian-heroine-wallpaper-sabse-badhkar-hum-2.jpg")
        list.add("https://qph.fs.quoracdn.net/main-qimg-a92551a9b90b6a9df5150157f4ef9ac5.webp")

        banner.setOnCarouselItemChangeListener {
            //                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_LONG).show();
        }


        banner.setOnCarouselItemClickListener { position, url ->
            Toast.makeText(this@NoteListActivity, url, Toast.LENGTH_LONG).show()
        }
        banner.initBanner(list)
    }


    private fun notifyDataSetChanged() {

        if(itemsArray.isEmpty()){
            hideShowView(true)
        }else{
            hideShowView(false)
        }

        mAdapter = NoteAdapter(itemsArray.reversed())
        rv_note.adapter = mAdapter
        mAdapter.notifyDataSetChanged()

    }

    fun btnAddNote(view: View) {
        startActivity(Intent(this@NoteListActivity, AddNoteActivity::class.java))
    }


    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            getDealsFromDb(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            getDealsFromDb(newText)
            return true
        }

        private fun getDealsFromDb(searchText: String) {
            pd.show()
            mViewModel.getSearchList("%$searchText%").observe(this@NoteListActivity, Observer {
                pd.hide()
                itemsArray=it
                notifyDataSetChanged()
            })

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(onQueryTextListener)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                val i = Intent(this@NoteListActivity, FilterActivity::class.java)
                i.putExtra("dateFilter", dateFilter)
                startActivityForResult(i, 1)
            }

        }
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                dateFilter = data!!.getStringExtra("dateFilter")!!
                if (dateFilter != "") {
                    pd.show()
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            mViewModel.findCreatedDates(dateFilter.toLong())
                                .observe(this@NoteListActivity, Observer {
                                    pd.hide()
                                    itemsArray = it
                                    notifyDataSetChanged()
                                })
                        }
                    }
                }
                Log.d(TAG, "onActivityResult: $dateFilter")
            }
        }
    }

    private fun hideShowView(flag: Boolean) {
        if (flag) {
            rv_note.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            emptyState.visibility = View.GONE
            rv_note.visibility = View.VISIBLE

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        observeLiveData()
    }
}
