package com.blowmymind.libgen.mainActivity_MVP;

import org.jsoup.nodes.Document;

/**
 * Created by Sandeep on 06-10-2017.
 */

public interface DataCallbackInterface {

    void searchFailed();

    void searchSuccess(Document doc);
}
