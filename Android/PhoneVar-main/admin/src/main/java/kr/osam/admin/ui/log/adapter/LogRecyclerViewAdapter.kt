package kr.osam.admin.ui.log.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.osam.admin.R
import kr.osam.admin.data.LogItem
import java.text.SimpleDateFormat
import java.util.*

class LogRecyclerViewAdapter(
    val logList: ArrayList<LogItem>,
    val context: Context
) : RecyclerView.Adapter<LogRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView
        val stateTextView: TextView
        val checkImageView: ImageView

        init {
            dateTextView = view.findViewById(R.id.date_text_view)
            stateTextView = view.findViewById(R.id.state_text_view)
            checkImageView = view.findViewById(R.id.is_checked_image_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_log_personal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = logList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTextView.text = logList[position].loggedAt
        holder.stateTextView.text = when (logList[position].logType) {
            0 -> "부팅"
            1 -> "실행"
            else -> "종료"
        }
        holder.stateTextView.setTextColor(
            Color.parseColor(
                when (logList[position].logType) {
                    0 -> "#DA4354"
                    1 -> "#000000"
                    else -> "#4390DA"
                }
            )
        )
        holder.checkImageView.setImageDrawable(
            if (!isLogPossible(logList[position].loggedAt))
                context.getDrawable(R.drawable.ic_check)
            else
                context.getDrawable(R.drawable.ic_cancel)
        )
    }

    private fun isLogPossible(loggedAt: String): Boolean {
        val format = SimpleDateFormat("HH:mm:ss")
        val logTime = loggedAt.split(" ")[1]
        val startTime = "18:00:00"
        val endTime = "21:00:00"
        val calendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        val startCalendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        val endCalendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        calendar.time = format.parse(logTime)
        startCalendar.time = format.parse(startTime)
        endCalendar.time = format.parse(endTime)
        return calendar.before(startCalendar) || calendar.after(endCalendar)
    }
}