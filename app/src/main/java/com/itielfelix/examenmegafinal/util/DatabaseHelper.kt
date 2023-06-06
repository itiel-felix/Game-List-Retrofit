package com.itielfelix.examenmegafinal.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.itielfelix.examenmegafinal.domain.item.GameItem


class DatabaseHelper(context: Context): SQLiteOpenHelper(context, "gameItems.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table gameItems " +
                "(id INTEGER primary key, " + //0
                "title TEXT not null, " + //1
                "thumbnail TEXT not null, " + //2
                "short_description TEXT not null," +//3
                "developer TEXT not null," +//3
                "freetogame_profile_url TEXT not null," +//4
                "game_url TEXT not null," +//5
                "genre TEXT not null," +//5
                "platform TEXT not null," +//6
                "publisher TEXT not null," +//7
                "release_date TEXT not null"/*8*/ +", save_date DATE not null" +//9
                ")"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteQuery = "drop table if exists gameItems"
        db!!.execSQL(deleteQuery)
        onCreate(db)
    }
    fun addRow(id: Int, title: String,
               thumbnail: String,
               short_description: String,
               developer: String,
               game_url: String,
               genre: String,
               platform: String,
               publisher: String,
               release_date: String,
               save_date:String, freetogame_profile_url:String){
        val rowInfo = ContentValues()
        rowInfo.put("id",id)
        rowInfo.put("title",title)
        rowInfo.put("thumbnail",thumbnail)
        rowInfo.put("short_description",short_description)
        rowInfo.put("developer",developer)
        rowInfo.put("game_url",game_url)
        rowInfo.put("genre",genre)
        rowInfo.put("platform",platform)
        rowInfo.put("publisher",publisher)
        rowInfo.put("release_date",release_date)
        rowInfo.put("save_date",save_date)
        rowInfo.put("freetogame_profile_url",freetogame_profile_url)

        val db = this.writableDatabase
        db.insert("gameItems",null,rowInfo)
        db.close()
    }

    fun deleteRow(id: Int){
        val db = this.writableDatabase
        Log.i("info",db.delete("gameItems", "id=$id", null).toString())
        db.close()
    }

    @SuppressLint("Recycle")
    fun listData():MutableList<GameItem>{
        val lista:MutableList<GameItem> = ArrayList()
        val db = this.readableDatabase
        val sql = "select * from gameItems"
        val result = db.rawQuery(sql,null)
        if(result.moveToFirst()){
            do {
                val newGameItem = GameItem(result.getString(0).toInt(), result.getString(1),result.getString(2), result.getString(3), result.getString(4),
                result.getString(5),result.getString(6),result.getString(7), result.getString(8), result.getString(9), result.getString(10))
                lista.add(newGameItem)

            }while(result.moveToNext())
            db.close()
        }
        return lista
    }

    fun isRegistered(id: Int):Boolean{
        var number = 0
        val db = this.readableDatabase
        val sql = "select * from gameItems where id = $id"
        val result = db.rawQuery(sql,null)
        if(result.moveToFirst()){
            do {
                number++
            }while(result.moveToNext())
            db.close()
        }
        Log.i("info", "number: $number")
        return number>0
    }

    fun getSavedDate(id: Int):String{

        val lista:MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val sql = "select * from gameItems where id = $id"
        val result = db.rawQuery(sql,null)
        if(result.moveToFirst()){
            do {
                lista.add(result.getString(11))
            }while(result.moveToNext())
            db.close()
        }
        Log.i("info",lista[0])
        return lista[0]
    }

}