package org.sil.cmb.employee_svc.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

public class GSONFactory {

    private GSONFactory() {}

    public static Gson getInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());

        return gsonBuilder.create();
    }
}
