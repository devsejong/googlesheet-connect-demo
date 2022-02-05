package net.chandol.demo.googlesheet.banner

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GoogleSheetBannerFetchService {

    fun fetchSheets(): List<Banner> {
        // 구글 인증처리
        val credential = connectGoogleCredentials()
        println("[GoogleSheetFetchService] google 인증 완료")

        // sheets 서비스 연결
        val sheetsService = connectSheetsService(credential)
        println("[GoogleSheetFetchService] google sheets 서비스 연결")

        return readValueRange(sheetsService, "배너!A4:E").map {
            Banner(
                id = it[0].toLong(),
                title = it[1],
                startDateTime = LocalDateTime.parse(it[2]),
                endDateTime = LocalDateTime.parse(it[3])
            )
        }
    }

    fun connectGoogleCredentials(): Credential {
        // Load client secrets.
        val inputStream = this::class.java.getResourceAsStream("/credentials.json")
        return GoogleCredential.fromStream(inputStream).createScoped(listOf(SheetsScopes.SPREADSHEETS_READONLY))
    }

    fun connectSheetsService(credential: Credential): Sheets {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()

        return Sheets.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("GoogleSheet Reader")
            .build()
    }

    fun readValueRange(sheetsService: Sheets, range: String): List<List<String>> {
        val response: ValueRange = sheetsService.spreadsheets().values()
            .get("1e4St5P7bwtP4Mf3lW_MRE3s15xrQEZNpvUiub-iv6Wg", range)
            .execute()

        // 단순하게 문자열 리스트로 반환하자..
        return response.getValues().map { row -> row.map { value -> value.toString() } }
    }
}
