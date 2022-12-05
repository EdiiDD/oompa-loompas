package com.edy.oompaloompas.data.local.roomsql

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.edy.oompaloompas.domain.interactors.Input


fun initQuery(filters: Input): SupportSQLiteQuery {
    val containsGenderFilter = filters.firstName != null
    val containsFirstNameFilter = filters.gender != null
    val containsProfessionFilter = filters.profession != null

    var query = "SELECT * FROM oompa_loompas "

    if (containsGenderFilter || containsFirstNameFilter || containsProfessionFilter) {
        query += "WHERE "
    }

    filters.firstName?.let {
        query += "first_name LIKE '%$it%' "
        if (containsFirstNameFilter || containsProfessionFilter) {
            query += "AND "
        }
    }

    filters.gender?.let {
        query += "gender is '$it' "
        if (containsProfessionFilter) {
            query += "AND "
        }
    }

    filters.profession?.let {
        query += "profession is '$it';"
    }
    return SimpleSQLiteQuery(query)
}