package com.tenx.bard.androidsqlsystem

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room.*
import com.tenx.bard.androidsqlsystem.api.AppDatabase
import com.tenx.bard.androidsqlsystem.feature.admin.AdminActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actionEditMode) {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
        return true
    }


}
