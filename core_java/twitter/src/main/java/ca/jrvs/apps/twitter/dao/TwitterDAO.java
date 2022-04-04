package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import org.apache.http.HttpResponse;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.InputStream;

public class TwitterDAO implements CrdDao {

    @Override
    public Object create(Object entity) {

        if (entity instanceof HttpResponse)
            return createWithHttpResponse((HttpResponse) entity);
        else
            return null;
    }

    private Tweet createWithHttpResponse(HttpResponse response) {

        InputStream inStream;
        try {
            inStream = response.getEntity().getContent();
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return new Tweet(parseIntoJsonObject(inStream));
    }

    private JsonObject parseIntoJsonObject(Object toParse) {

        JsonParser jsonParser;
        if (toParse instanceof InputStream) {

            jsonParser = Json.createParser((InputStream) toParse);
            jsonParser.next();
        }
        else
            jsonParser = (JsonParser) toParse;

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonParser.Event event = JsonParser.Event.START_OBJECT;

        while (jsonParser.hasNext() && event != JsonParser.Event.END_OBJECT) {

            event = jsonParser.next();

            if (event == JsonParser.Event.KEY_NAME) {

                String key = jsonParser.getString();
                jsonObjectBuilder.add(key, (JsonValue) parserEventValueExtractor(jsonParser));
            }
        }

        return jsonObjectBuilder.build();
    }

    private JsonArray parseIntoJsonArray(JsonParser parser) {

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonParser.Event event = parser.next();

        while (event != JsonParser.Event.END_ARRAY) {

            jsonArrayBuilder.add((JsonValue) parserEventValueExtractor(parser));
            event = parser.next();
        }

        return jsonArrayBuilder.build();
    }

    private Object parserEventValueExtractor(JsonParser parser) {

        JsonParser.Event format = parser.next();

        if (format == JsonParser.Event.VALUE_STRING || format == JsonParser.Event.VALUE_NUMBER)
            return parser.getString();
        else if (format == JsonParser.Event.VALUE_NULL)
            return  "";
        else if (format == JsonParser.Event.VALUE_TRUE)
            return true;
        else if (format == JsonParser.Event.VALUE_FALSE)
            return false;
        else if (format == JsonParser.Event.START_OBJECT)
            return parseIntoJsonObject(parser);
        else if (format == JsonParser.Event.START_ARRAY)
            return parseIntoJsonArray(parser);
    }

    @Override
    public Object findById(Object o) {
        return null;
    }

    @Override
    public Object deleteById(Object o) {
        return null;
    }
}
