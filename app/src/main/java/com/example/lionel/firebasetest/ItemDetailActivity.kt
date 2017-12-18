package com.example.lionel.firebasetest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_detail)

        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState==null) {
            val arguments = Bundle()

            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID))

            val fragment = ItemDetailFragment()

            fragment.arguments = arguments

            supportFragmentManager.beginTransaction().add(R.id.item_detail_container, fragment).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem)=
            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
                    true
                }
                else->super.onOptionsItemSelected(item)
            }
}
