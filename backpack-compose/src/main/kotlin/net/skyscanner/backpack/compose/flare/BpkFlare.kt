/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.flare

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Region
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.R
import net.skyscanner.backpack.compose.flare.internal.FlareHeight
import net.skyscanner.backpack.compose.flare.internal.FlareShape

enum class BpkFlareRadius {
  None,
  Medium,
}

enum class BpkFlarePointerDirection {
  Up,
  Down,
}

@Composable
fun BpkFlare(
  modifier: Modifier = Modifier,
  radius: BpkFlareRadius = BpkFlareRadius.None,
  pointerDirection: BpkFlarePointerDirection = BpkFlarePointerDirection.Down,
  background: Color = Color.Unspecified,
  insetContent: Boolean = false,
  contentAlignment: Alignment = Alignment.TopStart,
  propagateMinConstraints: Boolean = true,
  content: @Composable BoxScope.() -> Unit,
) {
  val contentPadding = when (insetContent) {
    true -> FlareHeight.dp
    false -> 0.dp
  }

  val context = LocalContext.current
  val pointerMask = remember {
    val pointerDrawable = AppCompatResources.getDrawable(context, R.drawable.flare_default_pointer)!!.apply {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    Bitmap.createBitmap(pointerDrawable.intrinsicWidth, pointerDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
      pointerDrawable.draw(Canvas(this))
    }
  }
  val radiusMask = remember {
    val radiiDrawable = AppCompatResources.getDrawable(context, R.drawable.flare_default_radius)!!.apply {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    Bitmap.createBitmap(radiiDrawable.intrinsicWidth, radiiDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
      radiiDrawable.draw(Canvas(this))
    }
  }


  Box(
    modifier = modifier
      .drawWithCache {
        val paint = Paint()
          .apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            isAntiAlias = true
          }

        val pointerYStart = size.height - pointerMask.height
        val pointerXStart = (size.width - pointerMask.width) / 2
        val pointerXEnd = pointerMask.width + pointerXStart
        val clipStartY = when (pointerDirection) {
          BpkFlarePointerDirection.Down -> pointerYStart
          BpkFlarePointerDirection.Up -> 0f
        }

        val clipHeight = when (pointerDirection) {
          BpkFlarePointerDirection.Down -> size.height
          BpkFlarePointerDirection.Up -> pointerMask.height.toFloat()
        }
        var saveCount = 0
        onDrawWithContent {
          drawIntoCanvas {
            val canvas = it.nativeCanvas

            saveCount = canvas.save()
            canvas.clipRect(0f, clipStartY, pointerXStart, clipHeight, Region.Op.DIFFERENCE)
            canvas.clipRect(size.width, clipStartY, pointerXEnd, clipHeight, Region.Op.DIFFERENCE)
          }
          drawContent()
          drawIntoCanvas {
            val canvas = it.nativeCanvas
            canvas.restoreToCount(saveCount)
            saveCount = canvas.save()

            if (pointerDirection == BpkFlarePointerDirection.Up) {
              canvas.rotate(180f, size.width / 2, size.height / 2)
            }
            canvas.drawBitmap(pointerMask, pointerXStart, pointerYStart, paint)
            canvas.restore()

            if (radius == BpkFlareRadius.Medium) {
              val radiusHeight = radiusMask.height
              val radiusWidth = radiusMask.width
              val radiusHalfHeight = radiusHeight.toFloat() / 2
              val radiusHalfWidth = radiusHeight.toFloat() / 2
              val pointerHeight = pointerMask.height

              canvas.save()

              // bottom left corner
              canvas.drawBitmap(radiusMask, 0f, size.height - pointerHeight - radiusHeight, paint)

              // top right corner
              canvas.rotate(180f, radiusHalfWidth, radiusHalfHeight)
              canvas.drawBitmap(radiusMask, -(size.width - radiusWidth), 0f, paint)

              // top left corner
              canvas.rotate(-90f, radiusHalfWidth, radiusHalfHeight)
              canvas.drawBitmap(radiusMask, 0f, 0f, paint)

              // bottom right corner
              canvas.rotate(-180f, radiusHalfWidth, radiusHalfHeight)
              canvas.drawBitmap(radiusMask, -(size.height - pointerHeight - radiusHeight), size.width - radiusWidth, paint)

              canvas.restoreToCount(saveCount)
            }
          }
        }
      }
      .background(background)
      .padding(
        top = when (pointerDirection) {
          BpkFlarePointerDirection.Up -> contentPadding
          BpkFlarePointerDirection.Down -> 0.dp
        },
        bottom = when (pointerDirection) {
          BpkFlarePointerDirection.Up -> 0.dp
          BpkFlarePointerDirection.Down -> contentPadding
        }
      ),
    propagateMinConstraints = propagateMinConstraints,
    contentAlignment = contentAlignment,
    content = content,
  )
}
