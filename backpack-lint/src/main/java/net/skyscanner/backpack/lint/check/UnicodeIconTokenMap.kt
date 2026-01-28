/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.lint.check

/**
 * Maps unicode symbols and emoji to their Backpack component equivalents.
 *
 * When a unicode character is used inside a Text/BpkText composable,
 * the lint rule suggests using the corresponding BpkIcon (or Backpack component)
 * with a proper contentDescription for accessibility.
 */
internal object UnicodeIconTokenMap {

    // region Stars & Ratings
    private val STARS = mapOf(
        '\u2605' to "BpkStarRating", // ★ Black Star
        '\u2606' to "BpkStarRating", // ☆ White Star
        '\u2B50' to "BpkStarRating", // ⭐ Star
    )
    // endregion

    // region Hearts & Save
    private val HEARTS = mapOf(
        '\u2665' to "BpkSaveButton", // ♥ Black Heart Suit
        '\u2764' to "BpkSaveButton", // ❤ Heavy Black Heart
    )
    // endregion

    // region Arrows
    private val ARROWS = mapOf(
        '\u2190' to "BpkIcon.ArrowLeft", // ←
        '\u2191' to "BpkIcon.ArrowUp", // ↑
        '\u2192' to "BpkIcon.ArrowRight", // →
        '\u2193' to "BpkIcon.ArrowDown", // ↓
        '\u2B05' to "BpkIcon.ArrowLeft", // ⬅
        '\u2B06' to "BpkIcon.ArrowUp", // ⬆
        '\u27A1' to "BpkIcon.ArrowRight", // ➡
        '\u2B07' to "BpkIcon.ArrowDown", // ⬇
    )
    // endregion

    // region Checkmarks & Crosses
    private val CHECKS = mapOf(
        '\u2713' to "BpkIcon.Tick", // ✓
        '\u2714' to "BpkIcon.Tick", // ✔
        '\u2717' to "BpkIcon.Close", // ✗
        '\u2718' to "BpkIcon.Close", // ✘
    )
    // endregion

    // region Common UI Symbols
    private val UI_SYMBOLS = mapOf(
        '\u2022' to "BpkIcon.Dot", // •
        '\u25B6' to "BpkIcon.Play", // ▶
        '\u26A0' to "BpkIcon.Exclamation", // ⚠
        '\u2139' to "BpkIcon.Information", // ℹ
        '\u2795' to "BpkIcon.Plus", // ➕
        '\u2796' to "BpkIcon.Minus", // ➖
        '\u2699' to "BpkIcon.Settings", // ⚙
        '\u270F' to "BpkIcon.Edit", // ✏
        '\u2709' to "BpkIcon.Mail", // ✉
    )
    // endregion

    // region Travel & Transport
    private val TRAVEL = mapOf(
        '\u2708' to "BpkIcon.Flight", // ✈
        '\u2615' to "BpkIcon.Cafe", // ☕
    )
    // endregion

    /**
     * Single-char unicode symbols mapped to Backpack components.
     * Used for fast per-character lookup.
     */
    val UNICODE_TO_BPK: Map<Char, String> = STARS + HEARTS + ARROWS + CHECKS + UI_SYMBOLS + TRAVEL

    /**
     * Multi-codepoint emoji mapped to Backpack components.
     * These require string-level matching since they use surrogate pairs or ZWJ sequences.
     */
    val EMOJI_TO_BPK: Map<String, String> = mapOf(
        // Lock
        "\uD83D\uDD12" to "BpkIcon.Lock", // 🔒
        "\uD83D\uDD13" to "BpkIcon.Unlock", // 🔓
        // Search
        "\uD83D\uDD0D" to "BpkIcon.Search", // 🔍
        "\uD83D\uDD0E" to "BpkIcon.Search", // 🔎
        // Calendar & Time
        "\uD83D\uDCC5" to "BpkIcon.Calendar", // 📅
        "\uD83D\uDCC6" to "BpkIcon.Calendar", // 📆
        "\uD83D\uDD14" to "BpkIcon.AlertActive", // 🔔
        // Location
        "\uD83D\uDCCD" to "BpkIcon.Pin", // 📍
        "\uD83D\uDCCC" to "BpkIcon.Pin", // 📌
        // Globe
        "\uD83C\uDF0D" to "BpkIcon.Globe", // 🌍
        "\uD83C\uDF0E" to "BpkIcon.Globe", // 🌎
        "\uD83C\uDF0F" to "BpkIcon.Globe", // 🌏
        // Communication
        "\uD83D\uDCE7" to "BpkIcon.Mail", // 📧
        "\uD83D\uDCDE" to "BpkIcon.PhoneCall", // 📞
        "\uD83D\uDCF1" to "BpkIcon.Mobile", // 📱
        // Transport
        "\uD83D\uDE97" to "BpkIcon.Cars", // 🚗
        "\uD83D\uDE8C" to "BpkIcon.Bus", // 🚌
        "\uD83D\uDE95" to "BpkIcon.Taxi", // 🚕
        "\uD83D\uDE86" to "BpkIcon.Train", // 🚆
        "\uD83D\uDE82" to "BpkIcon.Train", // 🚂
        // Places
        "\uD83C\uDFE8" to "BpkIcon.Hotels", // 🏨
        "\uD83C\uDFE0" to "BpkIcon.Hotels", // 🏠
        "\uD83C\uDFD6" to "BpkIcon.Beach", // 🏖
        // Food & Drink
        "\uD83C\uDF7D" to "BpkIcon.Food", // 🍽
        "\uD83C\uDF74" to "BpkIcon.Food", // 🍴
        "\uD83C\uDF7A" to "BpkIcon.Beer", // 🍺
        // Media
        "\uD83D\uDCF8" to "BpkIcon.Camera", // 📸
        "\uD83D\uDCF7" to "BpkIcon.Camera", // 📷
        "\uD83D\uDDA8" to "BpkIcon.Print", // 🖨
        // Actions
        "\uD83D\uDDD1" to "BpkIcon.Trash", // 🗑
        "\uD83D\uDCE4" to "BpkIcon.Upload", // 📤
        "\uD83D\uDCE5" to "BpkIcon.Download", // 📥
        "\uD83D\uDD17" to "BpkIcon.Paperclip", // 🔗
        "\uD83C\uDFAB" to "BpkIcon.Ticket", // 🎫
        "\uD83C\uDF9F" to "BpkIcon.Ticket", // 🎟
        // Gestures
        "\uD83D\uDC4D" to "BpkIcon.ThumbsUp", // 👍
        "\uD83D\uDC4E" to "BpkIcon.ThumbsDown", // 👎
        // Finance
        "\uD83D\uDCB3" to "BpkIcon.PaymentCard", // 💳
        "\uD83D\uDD11" to "BpkIcon.Key", // 🔑
        // Audio
        "\uD83D\uDD0A" to "BpkIcon.Speaker", // 🔊
        "\uD83D\uDD07" to "BpkIcon.SpeakerMute", // 🔇
        // Weather & Temperature
        "\uD83C\uDF21" to "BpkIcon.Temperature", // 🌡
    )
}
