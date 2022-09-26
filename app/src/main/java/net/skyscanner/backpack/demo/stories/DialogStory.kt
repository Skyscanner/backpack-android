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

package net.skyscanner.backpack.demo.stories

import android.content.Context
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.dialog.BpkDialog

class DialogStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val dialogType = arguments?.getString(TYPE)
      ?: savedInstanceState?.getInt(TYPE)

    val btn = view.findViewById<BpkButton>(R.id.open_dialog)
    btn.setOnClickListener {
      dialogsByType[dialogType]?.let {
        it.invoke(view.context).show()
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"
    const val TYPE = "type"

    infix fun of(type: String) = DialogStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_dialog)
      arguments?.putString(TYPE, type)
    }
  }

  private val dialogsByType = mapOf(
    "Normal" to { context: Context ->
      val dialog = BpkDialog(context)
      dialog.apply {
        title = "You are going to Tokyo!"
        description = "Your flight is all booked. Why not check out some hotels now?"
        icon = BpkDialog.Icon.Success(R.drawable.bpk_tick)

        addActionButton(
          BpkDialog.Button("Continue") {
            println("confirmed")
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Skip") {
            println("skipped")
            dialog.dismiss()
          }
        )
      }
    },

    "Warning" to { context: Context ->
      val dialog = BpkDialog(context)
      dialog.apply {
        title = """!#\$\Warning-0-1!#\$#\$?"""
        description = "Engine Overload.!^R? Please do something. Throw me into the freezer or something!!"
        icon = BpkDialog.Icon.Warning(R.drawable.bpk_lightning)

        addActionButton(
          BpkDialog.Button("Skip") {
            println("skipped")
            dialog.dismiss()
          }
        )
      }
    },

    "Delete" to { context: Context ->
      val dialog = BpkDialog(context, BpkDialog.Style.BOTTOM_SHEET)
      dialog.apply {
        title = "Delete?"
        description = "Are you sure you want to delete your avatar?"
        icon = BpkDialog.Icon.Danger(R.drawable.bpk_trash)

        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnCancelListener {
          println("canceled")
        }

        addActionButton(
          BpkDialog.Button("Delete") {
            println("deleted")
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Cancel") {
            println("canceled")
            dialog.dismiss()
          }
        )
      }
    },

    "Confirmation" to { context: Context ->
      val dialog = BpkDialog(context, BpkDialog.Style.BOTTOM_SHEET)
      dialog.apply {
        title = "You're almost ready to pack your bags!"
        description = """Your booking is being processed with Trip.com

As soon as your booking has been completed, your confirmation email will be sent to your email account.

Remember to check your junk mail folder

Please note down your reference number and contact Trip.com if you need to track, change or cancel your booking

Safe travels!"""
        icon = BpkDialog.Icon.Success(R.drawable.bpk_tick)
      }
    },

    "Links" to { context: Context ->
      val dialog = BpkDialog(context)
      dialog.apply {
        title = "Want to know when prices change?"
        description = "Create a price alert and we'll let you know changes for this route"
        icon = BpkDialog.Icon.Success(R.drawable.bpk_alert__active)

        addActionButton(
          BpkDialog.Button("Create") {
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Direct flights only") {
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("No, Thanks!") {
            dialog.dismiss()
          }
        )
      }
    },

    "Long" to { context: Context ->
      val dialog = BpkDialog(context)
      dialog.apply {
        title = "You are going to Tokyo!"
        description = Array(3) { getString(R.string.stub) }.joinToString()
        icon = BpkDialog.Icon.Success(R.drawable.bpk_tick)

        addActionButton(
          BpkDialog.Button("Continue") {
            println("confirmed")
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Skip") {
            println("skipped")
            dialog.dismiss()
          }
        )
      }
    },

    "Flare" to { context: Context ->
      val dialog = BpkDialog(context, BpkDialog.Style.FLARE)
      dialog.apply {
        title = "What is Backpack?"
        description = "The design system provides a single source of truth for the design language used at Skyscanner."

        addActionButton(
          BpkDialog.Button("Got it!") {
            println("confirmed")
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Whatev's") {
            println("skipped")
            dialog.dismiss()
          }
        )
      }
    },

    "FlareWithImage" to { context: Context ->
      val dialog = BpkDialog(context, BpkDialog.Style.FLARE)
      dialog.apply {
        title = "Where will you go?"
        description = "See the best Black Friday deals from out 1,200 travel partners. " +
          "Remember to keep checking back as we update deals throughput the weekend."
        Picasso.get().load("file:///android_asset/dialog_sample.jpg").into(image)

        addActionButton(
          BpkDialog.Button("Find your deal") {
            println("confirmed")
            dialog.dismiss()
          }
        )

        addActionButton(
          BpkDialog.Button("Ok, got it") {
            println("skipped")
            dialog.dismiss()
          }
        )
      }
    }
  )
}
