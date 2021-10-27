package com.kapil.todo

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class add_Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        var id=0
        var bundle: Bundle? =intent.extras
        try {
       id=bundle!!.getInt("ID",0)
            if(id!=0) {
                add_title.setText(bundle!!.getString("Name").toString())
                add_Content.setText(bundle!!.getString("Des").toString())
            }

        }
        catch(ex:Exception)
        {

        }

        btn_AddNote.setOnClickListener()
        {
            var dbManager=DbManager(this)
            var values=ContentValues()
            values.put("Title",add_title.text.toString())
            values.put("Description",add_Content.text.toString())
            if(id==0) {
            val ID=dbManager.Insert(values)

                if (ID > 0) {
                    Toast.makeText(this, "note is added", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "cannot add note", Toast.LENGTH_LONG).show()
                }
                var intent: Intent = Intent(this@add_Notes, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                var selectionArgs=arrayOf(id.toString())
                dbManager.Update(values,"ID=?",selectionArgs)
                var intent: Intent = Intent(this@add_Notes, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}