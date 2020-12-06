package com.exyfi.onlineshop;

import com.exyfi.onlineshop.utils.DbUtils;
import com.exyfi.onlineshop.utils.ServletUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * Product online shop application.
 */
public class Main {
    private static final Logger log = Log.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            DbUtils.initDb();
            ServletUtils.startServlet();
        } catch (Exception e) {
            log.warn("Application start failed", e);
        }
    }
}
