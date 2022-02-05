package net.chandol.demo.googlesheet

import net.chandol.demo.googlesheet.banner.GoogleSheetBannerFetchService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoogleSheetApplication(
    val googleSheetBannerFetchService: GoogleSheetBannerFetchService
) : CommandLineRunner {
    override fun run(vararg args: String) {
        val banners = googleSheetBannerFetchService.fetchSheets()

        println(banners)
    }
}

fun main(args: Array<String>) {
    runApplication<GoogleSheetApplication>(*args)
}
