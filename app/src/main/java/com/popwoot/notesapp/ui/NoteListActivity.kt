package com.popwoot.notesapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.popwoot.notesapp.R
import com.popwoot.notesapp.adapter.NoteAdapter
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.mvvm.NoteViewModel
import com.popwoot.notesapp.ui.base.BaseActivity
import com.popwoot.notesapp.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.content_main.*


class NoteListActivity : BaseActivity() {

    private val pd by lazy { CustomProgressDialog(this, false) }
    private val mViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java) }
    private lateinit var mAdapter: NoteAdapter
    private val TAG = this::class.java.simpleName
    private var dateFilter = ""
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        observeLiveData()
    }


    private fun observeLiveData() {
        mViewModel.getAllNotes().observe(this, Observer { data ->
            initAdapter(data)
        })
    }

    private fun initAdapter(data: List<NoteModel>) {
        mAdapter = NoteAdapter(data.reversed())
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
            mViewModel.getSearchList("%$searchText%").observe(this@NoteListActivity, Observer {
                mAdapter = NoteAdapter(it.reversed())
                rv_note.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
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
                    mViewModel.findCreatedDates(dateFilter.toLong())
                    observeLiveData()
                }
                Log.d(TAG, "onActivityResult: $dateFilter")
            }
        }
    }
}
