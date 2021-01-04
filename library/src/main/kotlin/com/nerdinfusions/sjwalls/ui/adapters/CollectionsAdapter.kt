package com.nerdinfusions.sjwalls.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.data.models.Collection
import com.nerdinfusions.sjwalls.extensions.context.boolean
import com.nerdinfusions.sjwalls.extensions.views.inflate
import com.nerdinfusions.sjwalls.ui.viewholders.CollectionViewHolder

class CollectionsAdapter(private val onClick: ((collection: Collection) -> Unit)? = null) :
    RecyclerView.Adapter<CollectionViewHolder>() {

    var collections: ArrayList<Collection> = ArrayList()
        set(value) {
            collections.clear()
            collections.addAll(value)
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position], onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val shouldBeFilled = parent.context.boolean(R.bool.enable_filled_collection_preview)
        return CollectionViewHolder(
            parent.inflate(
                if (shouldBeFilled) R.layout.item_collection_filled
                else R.layout.item_collection
            )
        )
    }

    override fun getItemCount(): Int = collections.size
}