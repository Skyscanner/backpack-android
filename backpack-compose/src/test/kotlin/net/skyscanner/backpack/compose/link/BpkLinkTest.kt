/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.link

import junit.framework.TestCase.assertEquals
import org.junit.Test

class BpkLinkTest {

    @Test
    fun `When convertToTextSegments() is called with empty text then it should return empty list`() {
        // Given
        val inputText = ""
        val urls = emptyList<String>()

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `When convertToTextSegments() is called with plain text and no processors then it should return single text segment`() {
        // Given
        val inputText = "This is plain text without any links"
        val urls = emptyList<String>()

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(1, result.size)
        assertEquals(TextSegment.Text(inputText), result[0])
    }

    @Test
    fun `When convertToTextSegments() is called with link0 pattern then it should return correct segments`() {
        // Given
        val expectedUrl = "https://terms.example.com"
        val inputText = "By continuing, you agree to our <link0>Terms of Service</link0>."
        val urls = listOf(expectedUrl)

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("By continuing, you agree to our "), result[0])
        assertEquals(TextSegment.Link("Terms of Service", expectedUrl), result[1])
        assertEquals(TextSegment.Text("."), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with link1 pattern then it should return correct segments`() {
        // Given
        val expectedUrl = "https://privacy.example.com"
        val inputText = "Please read our <link1>Privacy Policy</link1> for more information."
        val urls = listOf(expectedUrl)

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("Please read our "), result[0])
        assertEquals(TextSegment.Link("Privacy Policy", expectedUrl), result[1])
        assertEquals(TextSegment.Text(" for more information."), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with multiple links then it should process both links`() {
        // Given
        val termsUrl = "https://terms.example.com"
        val privacyUrl = "https://privacy.example.com"
        val inputText = "By continuing, you agree to our <link0>Terms of Service</link0> and <link1>Privacy Policy</link1>."
        val urls = listOf(
            termsUrl,
            privacyUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(5, result.size)
        assertEquals(TextSegment.Text("By continuing, you agree to our "), result[0])
        assertEquals(TextSegment.Link("Terms of Service", termsUrl), result[1])
        assertEquals(TextSegment.Text(" and "), result[2])
        assertEquals(TextSegment.Link("Privacy Policy", privacyUrl), result[3])
        assertEquals(TextSegment.Text("."), result[4])
    }

    @Test
    fun `When convertToTextSegments() is called with more than two links then it should process all links`() {
        // Given
        val termsUrl = "https://terms.example.com"
        val privacyUrl = "https://privacy.example.com"
        val helpUrl = "https://help.example.com"
        val inputText = "By continuing, you agree to our <link0>Terms of Service</link0>, <link1>Privacy Policy</link1>, and <link2>Help Center</link2>."
        val urls = listOf(
            termsUrl,
            privacyUrl,
            helpUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(7, result.size)
        assertEquals(TextSegment.Text("By continuing, you agree to our "), result[0])
        assertEquals(TextSegment.Link("Terms of Service", termsUrl), result[1])
        assertEquals(TextSegment.Text(", "), result[2])
        assertEquals(TextSegment.Link("Privacy Policy", privacyUrl), result[3])
        assertEquals(TextSegment.Text(", and "), result[4])
        assertEquals(TextSegment.Link("Help Center", helpUrl), result[5])
        assertEquals(TextSegment.Text("."), result[6])
    }

    @Test
    fun `When convertToTextSegments() is called with link at start of text then it should return correct segments`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "<link0>Click here</link0> to continue."
        val urls = listOf(
            expectedUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(2, result.size)
        assertEquals(TextSegment.Link("Click here", expectedUrl), result[0])
        assertEquals(TextSegment.Text(" to continue."), result[1])
    }

    @Test
    fun `When convertToTextSegments() is called with link at end of text then it should return correct segments`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "Please visit our <link0>website</link0>"
        val urls = listOf(
            expectedUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(2, result.size)
        assertEquals(TextSegment.Text("Please visit our "), result[0])
        assertEquals(TextSegment.Link("website", expectedUrl), result[1])
    }

    @Test
    fun `When convertToTextSegments() is called with only link text then it should return single link segment`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "<link0>Only Link</link0>"
        val urls = listOf(expectedUrl)

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(1, result.size)
        assertEquals(TextSegment.Link("Only Link", expectedUrl), result[0])
    }

    @Test
    fun `When convertToTextSegments() is called with custom regex then it should return link segment`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "This text has <style0>a link</style0> with custom regex."
        val urls = listOf(expectedUrl)

        // When
        val result = inputText.convertToTextSegments(urls, "<style0>(.*?)</style0>".toRegex())

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("This text has "), result[0])
        assertEquals(TextSegment.Link("a link", expectedUrl), result[1])
        assertEquals(TextSegment.Text(" with custom regex."), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with malformed link tags then it should return single text segment`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "This text has <link0>unclosed link tag."
        val urls = listOf(
            expectedUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(1, result.size)
        assertEquals(TextSegment.Text(inputText), result[0])
    }

    @Test
    fun `When convertToTextSegments() is called with empty link text then it should return correct segments`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "Click <link0></link0> here."
        val urls = listOf(
            expectedUrl,
        )

        // When
        val result = inputText.convertToTextSegments(urls)

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("Click "), result[0])
        assertEquals(TextSegment.Link("", expectedUrl), result[1])
        assertEquals(TextSegment.Text(" here."), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with button regex then it should return link segment`() {
        // Given
        val expectedUrl = "https://submit.com"
        val inputText = "Click this <button>Submit</button> to continue."
        val urls = listOf(expectedUrl)
        val buttonRegex = "<button>(.*?)</button>".toRegex()

        // When
        val result = inputText.convertToTextSegments(urls, buttonRegex)

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("Click this "), result[0])
        assertEquals(TextSegment.Link("Submit", expectedUrl), result[1])
        assertEquals(TextSegment.Text(" to continue."), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with complex regex then it should return link segment`() {
        // Given
        val expectedUrl = "https://example.com"
        val inputText = "Visit our <custom123>awesome site</custom123> today!"
        val urls = listOf(expectedUrl)
        val customRegex = "<custom123>(.*?)</custom123>".toRegex()

        // When
        val result = inputText.convertToTextSegments(urls, customRegex)

        // Then
        assertEquals(3, result.size)
        assertEquals(TextSegment.Text("Visit our "), result[0])
        assertEquals(TextSegment.Link("awesome site", expectedUrl), result[1])
        assertEquals(TextSegment.Text(" today!"), result[2])
    }

    @Test
    fun `When convertToTextSegments() is called with more than two custom links then it should process all custom links`() {
        // Given
        val firstUrl = "https://terms.example.com"
        val secondUrl = "https://privacy.example.com"
        val thirdUrl = "https://help.example.com"
        val inputText = "Please read our <custom>Terms</custom>, <custom>Privacy Policy</custom>, and <custom>Help Center</custom> before continuing."
        val urls = listOf(firstUrl, secondUrl, thirdUrl)
        val customRegex = "<custom>(.*?)</custom>".toRegex()

        // When
        val result = inputText.convertToTextSegments(urls, customRegex)

        // Then
        assertEquals(7, result.size)
        assertEquals(TextSegment.Text("Please read our "), result[0])
        assertEquals(TextSegment.Link("Terms", firstUrl), result[1])
        assertEquals(TextSegment.Text(", "), result[2])
        assertEquals(TextSegment.Link("Privacy Policy", secondUrl), result[3])
        assertEquals(TextSegment.Text(", and "), result[4])
        assertEquals(TextSegment.Link("Help Center", thirdUrl), result[5])
        assertEquals(TextSegment.Text(" before continuing."), result[6])
    }
}
