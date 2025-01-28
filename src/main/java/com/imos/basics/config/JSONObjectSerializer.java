package com.imos.basics.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.json.JSONObject;

/**
 * Class JSONObjectSerializer TODO
 *
 * @author Alok Ranjan Meher
 * @since 27-01-2025
 * @version 1.0
 */
public class JSONObjectSerializer extends StdSerializer<JSONObject> {

    public JSONObjectSerializer() {
        this(null);
    }

    public JSONObjectSerializer(Class<JSONObject> t) {
        super(t);
    }

    @Override
    public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeRawValue(value.toString());
    }
}