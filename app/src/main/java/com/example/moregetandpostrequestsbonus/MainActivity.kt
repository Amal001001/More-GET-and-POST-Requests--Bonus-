package com.example.moregetandpostrequestsbonus

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var et1: EditText
    lateinit var et2:EditText
    lateinit var button1: Button

    lateinit var button2: Button
    lateinit var rv: RecyclerView
    private lateinit var rvAdapter: RecyclerViewAdapter
    var users = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        button1 = findViewById(R.id.button1)
        button1.setOnClickListener { addUser() }

        users = arrayListOf()
        rv = findViewById(R.id.rv)
        rvAdapter = RecyclerViewAdapter(users)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        button2 = findViewById(R.id.button2)
        button2.setOnClickListener { getUsers() }
    }

    fun addUser(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (et1.text != null && et2.text != null) {
            // Hide Keyboard
            val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            val addedName = et1.text.toString()
            val addedLocation = et2.text.toString()
            et1.text.clear()
            et2.text.clear()

            val progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            apiInterface?.addUser(usersInformation(addedName,addedLocation))!!.enqueue(object :
                Callback<usersInformation> {
                override fun onResponse(call: Call<usersInformation>, response: Response<usersInformation>) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "$addedName added", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<usersInformation>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "something went wrong!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun getUsers(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<ArrayList<usersInfoItem>?>? = apiInterface!!.getUsers()

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        call?.enqueue(object : Callback<ArrayList<usersInfoItem>?> {
            override fun onResponse(call: Call<ArrayList<usersInfoItem>?>?, response: Response<ArrayList<usersInfoItem>?>?) {
                progressDialog.dismiss()
                var displayedResponse = ""
                val resource: ArrayList<usersInfoItem>? = response?.body()
                val allUsers = resource

                    for (user in allUsers!!) {
                        displayedResponse += "${user.name} : ${user.location}\n"
                    }
                users.add(displayedResponse)
                rvAdapter.notifyDataSetChanged()
                Log.d("TAG", "got all the users")
            }
            override fun onFailure(call: Call<ArrayList<usersInfoItem>?>?, t: Throwable?) {
                call?.cancel()
                Log.d("TAG", "onFailure")
            }
        })
    }

}