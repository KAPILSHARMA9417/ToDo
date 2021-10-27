package com.kapil.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.widget.Toast

class DbManager
{
    val dbName="MyNotes"
    val dbTable="Notes"
    val colId="ID"
    val colTitle="Title"
    val colDes="Description"
    val dbVersion=1
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY,$colTitle TEXT, $colDes TEXT);"
    var sqlDb:SQLiteDatabase?=null
    constructor(context: Context)
    {
     val db=DatabaseHelpreNotes(context)
        sqlDb=db.writableDatabase
        println("data base is at verion of = " +sqlDb!!.version)

    }
    inner class DatabaseHelpreNotes: SQLiteOpenHelper
    {
        var context:Context?=null
        constructor(context:Context):super(context,dbName,null,dbVersion)
        {
  this.context=context

        }
        override fun onCreate(p0: SQLiteDatabase?) {
           p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context,"DataBaseCreated",Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
         p0!!.execSQL("DROP TABLE IF EXISTS"+dbTable)
            onCreate(p0)
        }

    }
    fun Insert(value:ContentValues):Long
    {
   val ID =sqlDb!!.insert(dbTable, "",value)
        println("NEW CREATED ROW ID  = "+ID)
        return ID

    }
    fun Query(projection: Array<String>,selection:String,selectionArgs:Array<String>,sortOrder:String): Cursor
    {

    val qb= SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDb,projection,selection,selectionArgs,null,null,sortOrder)
        return cursor

    }
    fun Delete(selection:String,selectionArgs: Array<String>):Int
    {
        val count=sqlDb!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun Update(values:ContentValues,selection:String,selectionArgs: Array<String>,):Int
    {
        var count=sqlDb!!.update(dbTable,values,selection,selectionArgs)
        return count
    }
}