package com.pachkhede.goals
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "goals.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "goals"
        const val COLUMN_ID = "id"
        const val COLUMN_GOAL = "goal"
        const val COLUMN_DATE = "date"
        const val COLUMN_IS_COMPLETED = "isCompleted"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_GOAL TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_IS_COMPLETED INTEGER DEFAULT 0
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert a new goal
    fun insertGoal(goal: String, date: String, isCompleted: Boolean): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL, goal)
            put(COLUMN_DATE, date)
            put(COLUMN_IS_COMPLETED, if (isCompleted) 1 else 0)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // Get all goals
    fun getAllGoals(): List<Goal> {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val goals = mutableListOf<Goal>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val goal = getString(getColumnIndexOrThrow(COLUMN_GOAL))
                val date = getString(getColumnIndexOrThrow(COLUMN_DATE))
                val isCompleted = getInt(getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1
                goals.add(Goal(id, goal, date, isCompleted))
            }
        }
        cursor.close()
        return goals
    }

    // Update a goal
    fun updateGoal(id: Int, goal: String, date: String, isCompleted: Boolean): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL, goal)
            put(COLUMN_DATE, date)
            put(COLUMN_IS_COMPLETED, if (isCompleted) 1 else 0)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Delete a goal
    fun deleteGoal(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
