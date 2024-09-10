package hr.jjpietri.rocketlaunchinfo.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.jjpietri.rocketlaunchinfo.model.Item

private const val DB_NAME = "items.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "items"

private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::name.name} text not null, " +
        "${Item::inUse.name} integer not null, " +
        "${Item::capability.name} text not null, " +
        "${Item::maidenFlight.name} text not null, " +
        "${Item::humanRated.name} integer not null, " +
        "${Item::crewCapacity.name} integer not null, " +
        "${Item::imageUrl.name} text not null, " +
        "${Item::picturePath.name} text, " +
        "${Item::read.name} integer not null" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class RocketLaunchSqlHelper(context: Context?) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
), Repository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) = writableDatabase
        .delete(TABLE_NAME, selection, selectionArgs)

    override fun insert(values: ContentValues?) = writableDatabase.insert(
        TABLE_NAME, null, values
    )

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(
        TABLE_NAME,
        values,
        selection,
        selectionArgs
    )
}