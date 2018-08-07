package org.simple.clinic.registration.facility

import org.simple.clinic.util.RuntimePermissionResult
import org.simple.clinic.widgets.UiEvent

data class LocationPermissionChanged(val result: RuntimePermissionResult) : UiEvent

class RegistrationLocationPermissionScreenCreated : UiEvent