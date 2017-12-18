package com.example.lionel.firebasetest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lionel.firebasetest.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*


class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments.containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            mItem?.let {
                activity.toolbar_layout?.title = it.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        mItem?.let {
            rootView.item_detail.text = it.details
        }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
