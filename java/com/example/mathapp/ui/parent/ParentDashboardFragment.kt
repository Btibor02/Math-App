package com.example.mathapp.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mathapp.databinding.FragmentParentDashboardBinding

class ParentDashboardFragment : Fragment() {
    private var _binding: FragmentParentDashboardBinding? = null
    private val binding get() = _binding!!
    private val vm: ParentDashboardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentParentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.state.observe(viewLifecycleOwner) { s ->
            binding.tvChildName.text = s.childName
            binding.tvOverallAccuracy.text = getString(com.example.mathapp.R.string.overall_accuracy_fmt, s.overallAccuracy)
            binding.tvTotalSessions.text = getString(com.example.mathapp.R.string.total_sessions_fmt, s.totalSessions)
            binding.tvBestStreak.text = getString(com.example.mathapp.R.string.best_streak_fmt, s.bestStreak)
            binding.tvRecent.text = s.recentResults.joinToString("\n")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
