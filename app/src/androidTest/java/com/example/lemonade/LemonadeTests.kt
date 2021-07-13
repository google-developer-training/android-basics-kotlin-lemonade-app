/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests to be run on a physical device or emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class LemonadeTests : BaseTest() {

    @Before
    fun setup() {
        launchActivity<MainActivity>()
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun `test_initial_state`() {
        testState(R.string.lemon_select, R.drawable.lemon_tree)
    }

    /**
     * Test that the pick lemon functionality takes us to the "squeeze state"
     */
    @Test
    fun `test_picking_lemon_proceeds_to_squeeze_state`() {
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        testState(R.string.lemon_squeeze, R.drawable.lemon_squeeze)
    }

    /**
     * Test that the squeeze functionality takes us to the "drink state"
     */
    @Test
    fun `test_squeezing_lemon_proceeds_to_drink_state`() {
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        juiceLemon()
        testState(R.string.lemon_drink, R.drawable.lemon_drink)
    }

    /**
     * Test squeeze count snackbar
     */
    @Test
    fun `test_squeeze_count_snackbar_is_displayed`() {
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(longClick())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Squeeze count: 1, keep squeezing!")))
    }

    /**
     * Test that the drink functionality takes us to the "restart state"
     */
    @Test
    fun `test_drinking_juice_proceeds_to_restart_state`() {
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        juiceLemon()
        onView(withId(R.id.image_lemon_state)).perform(click())
        // Click image to progress state
        testState(R.string.lemon_empty_glass, R.drawable.lemon_restart)
    }

    /**
     * Test that the restart functionality takes us back to the "pick lemon state"
     */
    @Test
    fun `test_restarting_proceeds_to_pick_lemon_state`() {
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        juiceLemon()
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        // Click image to progress state
        onView(withId(R.id.image_lemon_state)).perform(click())
        testState(R.string.lemon_select, R.drawable.lemon_tree)
    }
}
