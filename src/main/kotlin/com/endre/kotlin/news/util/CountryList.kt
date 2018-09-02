package com.endre.kotlin.news.util

import com.google.common.io.Resources
import java.nio.charset.Charset

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
class CountryList {

    companion object {

        /*
            Companion Objects can be considered as singleton within the class.
            They are used to represent the equivalent of static state in Kotlin.
         */

        val countries: List<String>

        init {
            /*
                The "init" method is called when the object is created.

                Here using Guava library to help reading list of countries from resource file.

                Note the "::class.java" instead of ".class" to refer to a Class object
             */
            val url = Resources.getResource(CountryList::class.java, "/country/country_list.txt")

            /*
                "country" is defined as "val", which is a constant. This assignment here works
                only because we are inside a "init" method
             */
            countries = Resources.readLines(url, Charset.forName("UTF-8"))
        }

        fun isValidCountry(country: String): Boolean {
            return countries.any { it.equals(country, ignoreCase = true) }
        }
    }
}
