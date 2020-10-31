package kr.osam.admin.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.osam.admin.R
import kr.osam.admin.data.SoldierItem


class SoldierRecyclerViewAdapter(
    val context: Context,
    val soldierItemList: ArrayList<SoldierItem>,
    val recyclerViewOptionSelect: RecyclerViewOptionSelect,
    val recyclerViewClick: RecyclerViewClick
) : RecyclerView.Adapter<SoldierRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val organizationTextView: TextView
        val titleTextView: TextView
        val bodyTextView: TextView
        val isCheckedImageView: ImageView
        val optionButton: ImageView

        init {
            optionButton = view.findViewById(R.id.option_button)
            organizationTextView = view.findViewById(R.id.organization_text_view)
            titleTextView = view.findViewById(R.id.log_title_text_view)
            bodyTextView = view.findViewById(R.id.log_body_text_view)
            isCheckedImageView = view.findViewById(R.id.is_checked_image_view)
            view.setOnClickListener {
                recyclerViewClick.onRecyclerViewClickedListener(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = soldierItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.organizationTextView.text = soldierItemList[position].organization
        holder.titleTextView.text = soldierItemList[position].name
        holder.bodyTextView.text = soldierItemList[position].serviceNumber

        holder.isCheckedImageView.setImageDrawable(
            if (soldierItemList[position].statusCode == "200")
                context.getDrawable(R.drawable.ic_check)
            else
                context.getDrawable(R.drawable.ic_cancel)
        )

        holder.optionButton.setOnClickListener {
            val popup = PopupMenu(context, holder.optionButton)
            popup.inflate(R.menu.log_item_menu)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                recyclerViewOptionSelect.onRecyclerViewOptionSelectListener(position, item.itemId)
                true
            }
            popup.show()

        }
    }
}


interface RecyclerViewOptionSelect {
    fun onRecyclerViewOptionSelectListener(position: Int, menuID: Int)
}

interface RecyclerViewClick {
    fun onRecyclerViewClickedListener(position: Int)
}