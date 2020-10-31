package kr.osam.admin.ui.signin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.osam.admin.R
import kr.osam.admin.data.UnitItem
import kr.osam.admin.ui.koreanindexer.KoreanIndexerRecyclerView

class UnitBottomSheetAdapter(
    val unitList: List<UnitItem>,
    val context: Context
) : KoreanIndexerRecyclerView.KoreanIndexerRecyclerAdapter<UnitBottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textTextView: TextView
        var itemLinearLayout: LinearLayout

        init {
            textTextView = itemView.findViewById(R.id.text)
            itemLinearLayout = itemView.findViewById(R.id.item_linear_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_unit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = unitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTextView.setText(unitList.get(position).unitName)
        if (position % 2 == 0) {
            holder.itemLinearLayout.background =
                context.resources.getDrawable(R.drawable.bg_white_border_right_down)
            val layoutParams =
                holder.itemLinearLayout.layoutParams as GridLayoutManager.LayoutParams
            layoutParams.setMargins(10, 0, 0, 0)
        } else {
            holder.itemLinearLayout.background =
                context.resources.getDrawable(R.drawable.bg_white_border_left_down)
            val layoutParams =
                holder.itemLinearLayout.layoutParams as GridLayoutManager.LayoutParams
            layoutParams.setMargins(0, 0, 10, 0)
        }
    }
}