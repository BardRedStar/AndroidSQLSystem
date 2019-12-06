package com.tenx.bard.androidsqlsystem.feature.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.feature.add_pet.AddPetActivity
import com.tenx.bard.androidsqlsystem.feature.add_question.AddQuestionActivity
import com.tenx.bard.androidsqlsystem.feature.admin.models.AdminItem
import com.tenx.bard.androidsqlsystem.feature.show_probability.ShowProbabilityActivity
import kotlinx.android.synthetic.main.activity_admin.*


class AdminActivity : AppCompatActivity() {

    companion object {
        val ACTIVITY_ADD_CODE = 1
    }

    var isPetsOpened = true
    var items: List<AdminItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        title = "Admin Panel: Pets"

        val adapter = AdminItemAdapter(items)
        adapter.onItemLongClicked = {
            deleteItem(it)
        }
        adapter.onItemClicked = {
            openItemProbabilities(it)
        }
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val newActivityClass = if (isPetsOpened) AddPetActivity::class.java else AddQuestionActivity::class.java
            val intent = Intent(this, newActivityClass)
            startActivityForResult(intent, ACTIVITY_ADD_CODE)
        }

        navigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{
            isPetsOpened = it.itemId == R.id.navigation_pets
            title = if (isPetsOpened) "Admin Panel: Pets" else "Admin Panel: Questions"
            reloadData()
            return@OnNavigationItemSelectedListener true
        }

        reloadData()
    }

    private fun reloadData() {
        val app: MyApplication = application as MyApplication
        Thread {
            val newItems = if (isPetsOpened)
                app.database?.petQuestionDao()?.getAllPets()?.map { AdminItem(it.uid, it.name) }
            else
                app.database?.petQuestionDao()?.getAllQuestions()?.map { AdminItem(it.uid, it.text) }

            if (newItems != null) {
                runOnUiThread {
                    updateAdapterWithList(newItems)
                }
            }
        }.start()
    }

    private fun deleteItem(position: Int) {
        val app: MyApplication = application as MyApplication
        Thread {
            if (isPetsOpened) {
                app.database?.petQuestionDao()?.deletePet(items[position].id)
            } else {
                app.database?.petQuestionDao()?.deleteQuestion(items[position].id)
            }
            runOnUiThread{
                Toast.makeText(this, "${items[position].text} has been deleted!", Toast.LENGTH_SHORT).show()
                items = items.filter { it.id != items[position].id }
                updateAdapterWithList(items)
            }
        }.start()
    }

    private fun openItemProbabilities(position: Int) {
        val intent = Intent(this, ShowProbabilityActivity::class.java)
        intent.putExtra(ShowProbabilityActivity.KEY_ID, items.get(position).id)
        intent.putExtra(ShowProbabilityActivity.KEY_TYPE, if (isPetsOpened) ShowProbabilityActivity.TYPE_PET else ShowProbabilityActivity.TYPE_QUESTION )
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        reloadData()
    }

    private fun updateAdapterWithList(items: List<AdminItem>) {
        (rvList.adapter as AdminItemAdapter).objects = items
        this.items = items
        rvList.adapter!!.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
