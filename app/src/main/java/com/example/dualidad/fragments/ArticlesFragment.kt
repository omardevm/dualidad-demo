package com.example.dualidad.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dualidad.R
import com.example.dualidad.adapters.VideosAdapter
import com.example.dualidad.databinding.FragmentArticlesBinding
import com.example.dualidad.databinding.FragmentVideoBinding
import com.example.dualidad.models.Measure
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private lateinit var binding: FragmentArticlesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticlesBinding.bind(view)
        setUI()
    }

    private fun setUI() {
        val dataSet = loadList()
        binding.recyclerViewArticle.apply {
            setHasFixedSize(true)
            adapter = VideosAdapter(dataSet) {
                //openUri
                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(it.url)))
            }
        }

    }

    private fun loadList(): List<Measure> {
        val formList = arrayListOf<Measure>()
        try {
            val obj = JSONObject(loadJSONFromAsset() ?: "")
            val arr = obj.getJSONArray("articles")
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