package com.blowmymind.libgen.MainActivity_MVP;

import org.jsoup.nodes.Document;

/**
 * Created by Sandeep on 06-10-2017.
 */

public interface MainCallbackInterface {
    void newSearchTerm(String currentSearchTerm);

    void searchFailed();

    void searchSuccess(Document doc);
}
