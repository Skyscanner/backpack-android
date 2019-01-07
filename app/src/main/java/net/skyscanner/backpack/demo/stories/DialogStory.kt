package net.skyscanner.backpack.demo.stories

import android.content.Context
import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.dialog.BpkDialog

class DialogStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val dialogType = arguments?.getString(DialogStory.TYPE)
      ?: savedInstanceState?.getInt(DialogStory.TYPE)

    val btn = view.findViewById<BpkButton>(R.id.open_dialog)
    btn.setOnClickListener { it ->
      val let = dialogsByType[dialogType]?.let { it ->
        it.invoke(view.context).show()
      }
    }
  }

  companion object {
    const val LAYOUT_ID = "fragment_id"
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
        icon = BpkDialog.Icon(
          R.drawable.bpk_tick,
          R.color.bpkGreen500
        )

        addActionButton(BpkButton(context).apply {
          text = "Continue"
          setOnClickListener({
            println("confirmed")
            dialog.dismiss()
          })
        })

        addActionButton(BpkButton(context).apply {
          text = "Skip"
          type = BpkButton.Type.Secondary
          setOnClickListener({
            println("skipped")
            dialog.dismiss()
          })
        })
      }
    },

    "Warning" to { context: Context ->
      val dialog = BpkDialog(context)
      dialog.apply {
        title = """!#\$\Warning-0-1!#\$#\$?"""
        description = "Engine Overload.!^R? Please do something. Throw me into the freezer or something!!"
        icon = BpkDialog.Icon(
          R.drawable.bpk_lightning,
          R.color.bpkYellow500
        )

        addActionButton(BpkButton(context).apply {
          text = "Skip"
          type = BpkButton.Type.Secondary
          setOnClickListener({
            println("skipped")
            dialog.dismiss()
          })
        })
      }
    },

    "Delete" to { context: Context ->
      val dialog = BpkDialog(context, BpkDialog.Style.BOTTOM_SHEET)
      dialog.apply {
        title = "Delete?"
        description = "Are you sure you want to delete your avatar?"
        icon = BpkDialog.Icon(
          R.drawable.bpk_trash,
          R.color.bpkRed500
        )

        setCancelable(false)

        setOnCancelListener({
          println("canceled")
        })

        addActionButton(BpkButton(context).apply {
          text = "Delete"
          type = BpkButton.Type.Destructive
          setOnClickListener({
            println("deleted")
            dialog.dismiss()
          })
        })

        addActionButton(BpkButton(context).apply {
          text = "Cancel"
          type = BpkButton.Type.Secondary
          setOnClickListener({
            println("canceled")
            dialog.cancel()
          })
        })
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
        icon = BpkDialog.Icon(
          R.drawable.bpk_tick,
          R.color.bpkGreen500
        )
      }
    }
  )
}
