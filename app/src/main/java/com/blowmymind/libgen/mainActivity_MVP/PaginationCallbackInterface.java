package com.blowmymind.libgen.mainActivity_MVP;

/**
 * Created by Sandeep on 08-10-2017.
 */

public interface PaginationCallbackInterface {
    void success(int startIndex, int i);
    void failed();
}
