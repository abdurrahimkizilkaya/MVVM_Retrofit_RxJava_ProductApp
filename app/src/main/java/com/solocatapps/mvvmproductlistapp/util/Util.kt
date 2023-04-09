package com.solocatapps.mvvmproductlistapp.util

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder

class Util

    fun loadWithCoil(context: Context) : ImageLoader {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
        return imageLoader
    }
