package com.journaldev.androidrealmdatabase

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

class Skill : RealmObject() {
    @PrimaryKey
    @Required
    var skillName: String? = null

    companion object {
        const val PROPERTY_SKILL = "skillName"
    }
}