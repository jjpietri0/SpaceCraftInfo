package hr.jjpietri.rocketlaunchinfo.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SpaceCraftWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        SpaceCraftFetcher(context).fetchItems()
        return Result.success()
    }
}