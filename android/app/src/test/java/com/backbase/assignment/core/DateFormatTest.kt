package com.backbase.assignment.core

import org.junit.Test

class DateFormatTest {
    @Test
    fun `test that formatDate function work as expected`() {
        val dateString = "2019-03-15"
        assert(formatDate(dateString) == "March 15, 2019")
    }

    @Test
    fun `test that formatDate should return not available when as string is empty`() {
        val dateString = ""
        assert(formatDate(dateString) == "n/a")
    }
}