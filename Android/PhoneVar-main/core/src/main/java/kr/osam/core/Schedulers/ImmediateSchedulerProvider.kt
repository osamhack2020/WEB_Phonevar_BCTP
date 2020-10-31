package kr.osam.phonevar.util.Schedulers

import androidx.annotation.NonNull
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


object ImmediateSchedulerProvider : BaseSchedulerProvider {
    @NonNull
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    @NonNull
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    @NonNull
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}