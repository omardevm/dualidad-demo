package com.example.dualidad.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dualidad.R
import com.example.dualidad.adapters.VideosAdapter
import com.example.dualidad.databinding.FragmentVideoBinding
import com.example.dualidad.models.Measure
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class VideoFragment : Fragment(R.layout.fragment_video) {
    private lateinit var binding: FragmentVideoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoBinding.bind(view)
        setUI()
    }

    private fun setUI() {
        val dataSet = loadList()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = VideosAdapter(dataSet) {
                val action = VideoFragmentDirections.actionVideoFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        }

    }

    private fun loadList(): List<Measure> {
        val formList = arrayListOf<Measure>()
        try {
            val obj = JSONObject(loadJSONFromAsset() ?: "")
            val arr = obj.getJSONArray("video")
            for (i in 0 until arr.length()) {
                val inside = arr.getJSONObject(i)
                formList.add(
                    Measure(
                        inside.getString("url"),
                        inside.getString("title"),
                        inside.getString("desc")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            return formList
        }
    }

    private fun loadJSONFromAsset(): String? {
        return try {
            val `is`: InputStream = requireActivity().assets.open("mediums.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}