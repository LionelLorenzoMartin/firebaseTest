package com.example.lionel.firebasetest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.lionel.firebasetest.dummy.DummyContent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*



class ItemListActivity : AppCompatActivity() {

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("contactos")


        fab.setOnClickListener { view ->

            var c= Contactos()
            c.nombre="UnoCualquiera"
            c.telefono=644556677
            myRef.child("contactos").push().setValue(c)

            setupRecyclerView(item_list)
        }

        val menuListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("data change")

                DummyContent.clearItems()
                for (objj  in dataSnapshot.children){

                    val contacto = objj.getValue<Contactos>(Contactos::class.java)
                    println("Telefono > " + contacto.nombre)
                    DummyContent.addItem(DummyContent.createDummyItem(contacto))
                }
                setupRecyclerView(item_list)
            }
        }

        myRef.child("contactos").addValueEventListener(menuListener)

        if (item_detail_container != null) {
            mTwoPane = true
        }
        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: ItemListActivity,
                                        private val mValues: List<DummyContent.DummyItem>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (mTwoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle()
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mIdView.text = item.id
            holder.mContentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mContentView: TextView = mView.content
        }
    }
}
