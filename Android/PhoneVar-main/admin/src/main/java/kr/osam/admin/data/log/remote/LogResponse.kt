package kr.osam.admin.data.log.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kr.osam.admin.data.LogItem

data class LogResponse(
    @SerializedName("userId") @Expose var userId: Int,
    @SerializedName("userLogs") @Expose var userLogs: List<UserLogs>
)

data class UserLogs(
    @SerializedName("logType") @Expose var logType: Int,
    @SerializedName("loggedAt") @Expose var loggedAt: String,
    @SerializedName("createdAt") @Expose var createdAt: String
)


fun UserLogs.convertToModel(): LogItem {
    return LogItem(logType = this.logType, loggedAt = this.loggedAt, createdAt = this.createdAt)
}
