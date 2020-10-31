package kr.osam.phonevar.data.source.log.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogResponse(
    @SerializedName("success") @Expose var isSuccess: Boolean
)