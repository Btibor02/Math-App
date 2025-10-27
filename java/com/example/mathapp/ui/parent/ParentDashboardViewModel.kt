package com.example.mathapp.ui.parent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ParentDashboardState(
    val childName: String = "Student",
    val overallAccuracy: Int = 86,
    val totalSessions: Int = 24,
    val bestStreak: Int = 9,
    val recentResults: List<String> = listOf(
        "G4 • × • 8/10",
        "G3 • + • 10/10",
        "G5 • ÷ • 7/10",
        "G2 • − • 9/10"
    )
)

class ParentDashboardViewModel : ViewModel() {
    private val _state = MutableLiveData(ParentDashboardState())
    val state: LiveData<ParentDashboardState> = _state
}
