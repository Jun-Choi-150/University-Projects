package app.Users;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Set;

public class FriendSerializer extends JsonSerializer<Set<User>> {

    @Override
    public void serialize(Set<User> friends, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (User friend : friends) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", friend.getId());
            jsonGenerator.writeStringField("name", friend.getName());
            jsonGenerator.writeStringField("emailId", friend.getEmailId());
            jsonGenerator.writeStringField("profilePic", friend.getProfilePic().toString());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
