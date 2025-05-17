package database

import entity.Transaction
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


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

        val transactions = list()
        Log.d("DatabaseHandler", "Lista de transações após inserção: $transactions")
    }

    fun list(): MutableList<Transaction> {
        val db = this.writableDatabase
        val transaction = db.query("transactions", null, null, null, null, null, null)
        val transactions = mutableListOf<Transaction>()

        while (transaction.moveToNext()){
            val transaction = Transaction(
                transaction.getInt(ID),
                transaction.getString(TYPE),
                transaction.getString(DETAILS),
                transaction.getDouble(VALUE),
                transaction.getString(DATE)
            )
            transactions.add(transaction)

        }

        return transactions
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "transactions"

        private const val ID = 0
        private const val TYPE = 1
        private const val DETAILS = 2
        private const val VALUE = 3
        private const val DATE = 4

    }
}