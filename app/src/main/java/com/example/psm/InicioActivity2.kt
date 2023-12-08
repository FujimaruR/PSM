package com.example.psm

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.navigation.NavigationView
import android.util.Base64

class InicioActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio2)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = navigationView.getHeaderView(0)
        val textView: TextView = headerView.findViewById(R.id.tvNickName)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_doctores -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,DoctoresFragment()).commit()
            R.id.nav_especies -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,EspeciesFragment()).commit()
            R.id.nav_CerrarSesion -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,HomeFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

}