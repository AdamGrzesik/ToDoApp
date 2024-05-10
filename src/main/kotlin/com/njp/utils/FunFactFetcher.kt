package com.njp.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

@Service
class FunFactFetcher {
    fun fetchRandomFunFact(): String? {
        val apiUrl = "https://uselessfacts.jsph.pl/api/v2/facts/random"
        var responseContent: String? = null
        try {
            val url: URL = URI.create(apiUrl).toURL()
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val responseCode: Int = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader: BufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?
                val response = StringBuilder()

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                responseContent = response.toString()
            } else {
                println("Error: Unable to fetch data from API")
            }
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val objectMapper = ObjectMapper()
        val rootNode: JsonNode = objectMapper.readTree(responseContent)
        val textContent: String? = rootNode.get("text")?.asText()
        return textContent
    }
}