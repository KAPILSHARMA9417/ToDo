package com.kapil.todo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ticket.view.*

class MainActivity : AppCompatActivity() {
    var noteList=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoadQuery("%")
     }

    override fun onResume() {
        LoadQuery("%")
        Toast.makeText(this@MainActivity,"onResume",Toast.LENGTH_LONG).show()


        super.onResume()

    }


    fun LoadQuery(title:String)
    {
        val projections=arrayOf("ID","title","Description")
        var dbManager=DbManager(this)
        val selectionArgs=arrayOf(title)
        val cursor=dbManager.Query(projections,"title like ?",selectionArgs,"title")
        noteList.clear()
        if(cursor.moveToFirst())
        {
            do {
               val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("title"))
                val Description=cursor.getString(cursor.getColumnIndex("Description"))
                noteList.add(Note(ID,Title,Description))
              }
            while(cursor.moveToNext())
        }
        var myNotesAdapter=MyNotesAdapter(noteList)
        lvNotes.adapter=myNotesAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        var sv=menu!!.findItem(R.id.menu_Search).actionView as SearchView
        sv.setOnCloseListener (object:SearchView.OnCloseListener {
           override fun onClose():Boolean{
               LoadQuery("%")
              return true
           }
        }
        )
        val sm=getSystemService(Context.SEARCH_SERVICE)as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String): Boolean {

                return false

            }


            override fun onQueryTextChange(p0: String?): Boolean {
             //   Toast.makeText(applicationContext,"yes",Toast.LENGTH_LONG).show()
                LoadQuery("%"+p0+"%")

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null)
        {
            when(item.itemId)
            {
                R.id.menu_AddNote->
                {
                    var intent= Intent(this@MainActivity,add_Notes::class.java)
                    startActivity(intent)
                }


            }

        }
        return super.onOptionsItemSelected(item)
    }
    inner class MyNotesAdapter: BaseAdapter
    {

        var listNote=ArrayList<Note>()
        constructor(note: ArrayList<Note>)
        {
        this.listNote=note
        }

        override fun getCount(): Int {
            return  listNote.size
        }

        override fun getItem(p0: Int): Any {
            return listNote[p0]
        }

        override fun getItemId(p0: Int): Long {
            return listNote[p0].nodeId!!.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
    var view =layoutInflater.inflate(R.layout.activity_ticket,null )
            var myNote=listNote[p0]
            view.tv_Title.text=myNote.nodeName
            view.tv_Content.text=myNote.nodeDes
            view.btn_delete.setOnClickListener()
            {
                var db=DbManager(this@MainActivity)
                var args= arrayOf(myNote.nodeId.toString())
                db.Delete("ID=?",args)
                LoadQuery("%")

            }
            view.btn_edit.setOnClickListener()
            {gotoUpdate( myNote)

            }
             return view

        }
        fun gotoUpdate(note:Note)
        {
var intent=Intent(this@MainActivity,add_Notes::class.java)
            intent.putExtra("ID",note.nodeId)
            intent.putExtra("Name",note.nodeName)
            intent.putExtra("Des",note.nodeDes)
            startActivity(intent)
        }

    }
}

