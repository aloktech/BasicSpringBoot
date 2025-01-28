package com.imos.basics.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.json.JSONArray;

/**
 * Class JSONArraySerializer TODO
 *
 * @author Alok Ranjan Meher
 * @since 27-01-2025
 * @version 1.0
 */
public class JSONArraySerializer extends StdSerializer<JSONArray> {
  protected JSONArraySerializer() {
    super(JSONArray.class);
  }

  protected JSONArraySerializer(Class<JSONArray> t) {
    super(t);
  }

  @Override
  public void serialize(
      JSONArray objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeRawValue(objects.toString());
  }
}
