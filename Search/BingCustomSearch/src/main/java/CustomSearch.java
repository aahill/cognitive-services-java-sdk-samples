/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

//package com.microsoft.azure.cognitiveservices.search.customsearch.samples;
package main.java;

import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchAPI;
import com.microsoft.azure.cognitiveservices.search.customsearch.BingCustomSearchManager;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.SearchResponse;
import com.microsoft.azure.cognitiveservices.search.customsearch.models.WebPage;

/**
 * Sample code for custom searching news using Bing Custom Search, an Azure Cognitive Service.
 *  - Custom search for "Xbox" and print out name and url for the first web page in the results list.
 */
public class CustomSearch {
    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing Custom Search API client
     * @return true if sample runs successfully
     */
    
    public static void CallCustomSearch(BingCustomSearchAPI client, String customConfigId, String market, String query) {
        try {
            System.out.println("Searched for Query: " + query);
            // If you do not have a customConfigId, you can also use 1 as your value when setting your environment variable.
            SearchResponse webData = client.bingCustomInstances().search()
                .withCustomConfig(customConfigId != null ? Long.valueOf(customConfigId) : 1)
                .withQuery(query)
                .withMarket(market)
                .execute();

            printFirstResult(webData);

        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
    }

    public static void printFirstResult(SearchResponse webData){
        if (webData != null && webData.webPages() != null && webData.webPages().value().size() > 0){
            // find the first web page result
            WebPage firstWebPagesResult = webData.webPages().value().get(0);

            if (firstWebPagesResult != null) {
                System.out.println(String.format("Webpage Results#%d", webData.webPages().value().size()));
                System.out.println(String.format("First web page name: %s ", firstWebPagesResult.name()));
                System.out.println(String.format("First web page URL: %s ", firstWebPagesResult.url()));
            } else {
                System.out.println("No web results were found.");
            }
        } else {
            System.out.println("No web data was received..");
        }
    }

    
    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {

            // Set the environment variables below for your OS, and then be sure to reopen your command prompt or IDE.
            // If you do not have a customConfigId, you can also use 1 as your value when setting your environment variable.

            final String subscriptionKey = System.getenv("AZURE_BING_CUSTOM_SEARCH_API_KEY");
            final String customConfigId = "1";//System.getenv("AZURE_BING_SAMPLES_CUSTOM_CONFIG_ID");
            final String market = "en-us";

            BingCustomSearchAPI client = BingCustomSearchManager.authenticate(subscriptionKey);
            String query = "Xbox";

            CallCustomSearch(client, customConfigId, market, query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}