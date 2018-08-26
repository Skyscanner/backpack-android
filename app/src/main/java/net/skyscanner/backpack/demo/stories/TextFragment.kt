package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R

class TextFragment : ComponentDetailFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

}

