package org.simple.clinic.bp.sync

import com.f2prateek.rx.preferences2.Preference
import io.reactivex.Completable
import org.simple.clinic.bp.BloodPressureMeasurement
import org.simple.clinic.bp.BloodPressureRepository
import org.simple.clinic.sync.ModelSync
import org.simple.clinic.sync.SyncCoordinator
import org.simple.clinic.util.Optional
import javax.inject.Inject
import javax.inject.Named

class BloodPressureSync @Inject constructor(
    private val syncCoordinator: SyncCoordinator,
    private val api: BloodPressureSyncApiV2,
    private val repository: BloodPressureRepository,
    @Named("last_bp_pull_token") private val lastPullToken: Preference<Optional<String>>
): ModelSync {

  override fun sync(): Completable = Completable.mergeArrayDelayError(push(), pull())

  override fun push() = syncCoordinator.push(repository) { api.push(toRequest(it)) }

  override fun pull() = syncCoordinator.pull(repository, lastPullToken, api::pull)

  private fun toRequest(measurements: List<BloodPressureMeasurement>): BloodPressurePushRequest {
    val payloads = measurements.map { it.toPayload() }
    return BloodPressurePushRequest(payloads)
  }
}
