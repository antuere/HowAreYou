package antuere.data.localDatabase.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            "ALTER TABLE day_table ADD COLUMN date_text TEXT DEFAULT 0 NOT NULL "
        )
        database.execSQL(
            "CREATE TABLE days_new (dayId INTEGER NOT NULL, date INTEGER NOT NULL, image_id INTEGER," +
                    "day_text TEXT, date_text TEXT NOT NULL, PRIMARY KEY (dayId))"
        )
        database.execSQL(
            "INSERT INTO days_new (dayId, date, image_id, day_text, date_text)" +
                    "SELECT dayId, date, image_id, day_text, date_text FROM day_table "
        )

        database.execSQL("DROP TABLE day_table")

        database.execSQL("ALTER TABLE days_new RENAME TO day_table")
    }

}