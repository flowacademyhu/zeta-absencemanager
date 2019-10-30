package hu.flowacademy.zetaabsencemanager.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hu.flowacademy.zetaabsencemanager.model.User;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserSerializer extends JsonSerializer {

  @Override
  public void serialize(Object o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    if (o instanceof Collection) {
      var users = (Collection<User>) o;
      jsonGenerator.writeObject(
          users.stream().peek(user -> user.setGroup(null)).collect(Collectors.toList()));
    }
  }
}
