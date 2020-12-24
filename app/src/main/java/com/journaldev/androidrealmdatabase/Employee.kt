package com.journaldev.androidrealmdatabase

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
//
class Employee : RealmObject() {
    @PrimaryKey
    @Required
    var name: String? = null
    var age = 0
    var skills: RealmList<Skill?>? = null

    companion object {
        const val PROPERTY_NAME = "name"
        const val PROPERTY_AGE = "age"
    }
}