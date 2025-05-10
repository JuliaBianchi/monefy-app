package database

import entity.Transaction
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context : Context ) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT, details TEXT, value REAL, date TEXT )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun insert(transaction: Transaction) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put("type", transaction.type)
        values.put("details", transaction.details)
        values.put("value", transaction.value)
        values.put("date", transaction.date)

        db.insert("transactions", null, values)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "transactions"

    }
}