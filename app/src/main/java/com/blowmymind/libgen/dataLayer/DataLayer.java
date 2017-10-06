package com.blowmymind.libgen.DataLayer;

import com.blowmymind.libgen.MainActivity_MVP.DataCallbackInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Sandeep on 06-10-2017.
 */

public class DataLayer {

    private final String searchTerm;

    /**
     * @param searchTerm Pass encoded search term
     */
    public DataLayer(String searchTerm){
        this.searchTerm = searchTerm;
    }

    public void initSearch(final DataCallbackInterface callbackInterface){
        FetcherThread fetcherThread = new FetcherThread(callbackInterface);
        fetcherThread.start();
    }

    private class FetcherThread extends Thread{

        private final DataCallbackInterface callbackInterface;

        FetcherThread(DataCallbackInterface callbackInterface){
            this.callbackInterface = callbackInterface;
        }

        @Override
        public void run() {
            Document doc;
            try {
                doc = Jsoup
                        .connect(URLConstants.basic_url)
                        .userAgent(URLConstants.userAgent)
                        .data("req",searchTerm)
                        .timeout(4000)
                        .get();
            } catch (IOException e) {
                callbackInterface.searchFailed();
                return;
            }
            callbackInterface.searchSuccess(doc);
        }
    }
}
