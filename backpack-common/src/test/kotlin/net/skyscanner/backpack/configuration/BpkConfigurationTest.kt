package net.skyscanner.backpack.configuration

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BpkConfigurationTest {

    @Test
    fun `setConfigs sets chipConfig when called first time then throws the second time`() {
        BpkConfiguration.setConfigs(chipConfig = true)
        assertNotNull(BpkConfiguration.chipConfig)
        assertEquals(BpkConfiguration.chipConfig, BpkConfiguration.BpkExperimentalComponent.BpkChip)
        val result = runCatching { BpkConfiguration.setConfigs(buttonConfig = true) }
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }
}
