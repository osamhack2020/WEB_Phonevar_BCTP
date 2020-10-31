package kr.osam.phonevar.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.osam.phonevar.R
import kr.osam.phonevar.data.LogItem

class LogRecyclerViewAdapter(
    val logItemList: ArrayList<LogItem>
) : RecyclerView.Adapter<LogRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView
        val stateTextView: TextView
        val syncImageView: ImageView

        init {
            dateTextView = view.findViewById(R.id.date_text_view)
            stateTextView = view.findViewById(R.id.state_text_view)
            syncImageView = view.findViewById(R.id.sync_image_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = logItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTextView.text = logItemList[position].loggedAt
        holder.stateTextView.text = when (logItemList[position].logType) {
            0 -> "부팅"
            1 -> "실행"
            else -> "종료"
        }
        holder.stateTextView.setTextColor(
            Color.parseColor(
                when (logItemList[position].logType) {
                    0 -> "#DA4354"
                    1 -> "#000000"
                    else -> "#4390DA"
                }
            )
        )

        holder.syncImageView.visibility =
            if (logItemList[position].isSync) View.VISIBLE else View.INVISIBLE
    }
}