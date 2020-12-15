package com.journaldev.androidrealmdatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmList
import io.realm.exceptions.RealmPrimaryKeyConstraintException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var btnAdd: Button? = null
    var btnRead: Button? = null
    var btnUpdate: Button? = null
    var btnDelete: Button? = null
    var btnDeleteWithSkill: Button? = null
    var btnFilterByAge: Button? = null
    var inName: EditText? = null
    var inAge: EditText? = null
    var inSkill: EditText? = null
    var textView: TextView? = null
    var txtFilterBySkill: TextView? = null
    var txtFilterByAge: TextView? = null
    var mRealm: Realm? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        mRealm = Realm.getDefaultInstance()
    }

    private fun initViews() {
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(this)
        btnRead = findViewById(R.id.btnRead)
        btnRead.setOnClickListener(this)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener(this)
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener(this)
        btnDeleteWithSkill = findViewById(R.id.btnDeleteWithSkill)
        btnDeleteWithSkill.setOnClickListener(this)
        btnFilterByAge = findViewById(R.id.btnFilterByAge)
        btnFilterByAge.setOnClickListener(this)
        textView = findViewById(R.id.textViewEmployees)
        txtFilterBySkill = findViewById(R.id.txtFilterBySkill)
        txtFilterByAge = findViewById(R.id.txtFilterByAge)
        inName = findViewById(R.id.inName)
        inAge = findViewById(R.id.inAge)
        inSkill = findViewById(R.id.inSkill)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAdd -> addEmployee()
            R.id.btnRead -> readEmployeeRecords()
            R.id.btnUpdate -> updateEmployeeRecords()
            R.id.btnDelete -> deleteEmployeeRecord()
            R.id.btnDeleteWithSkill -> deleteEmployeeWithSkill()
            R.id.btnFilterByAge -> filterByAge()
        }
    }

    private fun addEmployee() {
        var realm: Realm? = null
        try {
            realm = Realm.getDefaultInstance()
            realm.executeTransaction(Realm.Transaction { realm ->
                try {
                    if (!inName!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                        val employee = Employee()
                        employee.name = inName!!.text.toString().trim { it <= ' ' }
                        if (!inAge!!.text.toString().trim { it <= ' ' }.isEmpty()) employee.age = inAge!!.text.toString().trim { it <= ' ' }.toInt()
                        val languageKnown = inSkill!!.text.toString().trim { it <= ' ' }
                        if (!languageKnown.isEmpty()) {
                            var skill = realm.where(Skill::class.java).equalTo(Skill.Companion.PROPERTY_SKILL, languageKnown).findFirst()
                            if (skill == null) {
                                skill = realm.createObject(Skill::class.java, languageKnown)
                                realm.copyToRealm<Skill?>(skill)
                            }
                            employee.skills = RealmList()
                            employee.skills!!.add(skill)
                        }
                        realm.copyToRealm(employee)
                    }
                } catch (e: RealmPrimaryKeyConstraintException) {
                    Toast.makeText(applicationContext, "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show()
                }
            })
        } finally {
            realm?.close()
        }
    }

    private fun readEmployeeRecords() {
        mRealm!!.executeTransaction { realm ->
            val results = realm.where(Employee::class.java).findAll()
            textView!!.text = ""
            for (employee in results) {
                textView!!.append(employee.name + " age: " + employee.age + " skill: " + employee.skills!!.size)
            }
        }
    }

    private fun updateEmployeeRecords() {
        mRealm!!.executeTransaction { realm ->
            if (!inName!!.text.toString().trim { it <= ' ' }.isEmpty()) {
                var employee = realm.where(Employee::class.java).equalTo(Employee.Companion.PROPERTY_NAME, inName!!.text.toString()).findFirst()
                if (employee == null) {
                    employee = realm.createObject(Employee::class.java, inName!!.text.toString().trim { it <= ' ' })
                }
                if (!inAge!!.text.toString().trim { it <= ' ' }.isEmpty()) employee!!.age = inAge!!.text.toString().trim { it <= ' ' }.toInt()
                val languageKnown = inSkill!!.text.toString().trim { it <= ' ' }
                var skill = realm.where(Skill::class.java).equalTo(Skill.Companion.PROPERTY_SKILL, languageKnown).findFirst()
                if (skill == null) {
                    skill = realm.createObject(Skill::class.java, languageKnown)
                    realm.copyToRealm<Skill?>(skill)
                }
                if (!employee!!.skills!!.contains(skill)) employee.skills!!.add(skill)
            }
        }
    }

    private fun deleteEmployeeRecord() {
        mRealm!!.executeTransaction { realm ->
            val employee = realm.where(Employee::class.java).equalTo(Employee.Companion.PROPERTY_NAME, inName!!.text.toString()).findFirst()
            employee?.deleteFromRealm()
        }
    }

    private fun deleteEmployeeWithSkill() {
        mRealm!!.executeTransaction { realm ->
            val employees = realm.where(Employee::class.java).equalTo("skills.skillName", inSkill!!.text.toString().trim { it <= ' ' }).findAll()
            employees.deleteAllFromRealm()
        }
    }

    private fun filterByAge() {
        mRealm!!.executeTransaction { realm ->
            val results = realm.where(Employee::class.java).greaterThanOrEqualTo(Employee.Companion.PROPERTY_AGE, 25).findAllSortedAsync(Employee.Companion.PROPERTY_NAME)
            txtFilterByAge!!.text = ""
            for (employee in results) {
                txtFilterByAge!!.append(employee.name + " age: " + employee.age + " skill: " + employee.skills!!.size)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mRealm != null) {
            mRealm!!.close()
        }
    }
}