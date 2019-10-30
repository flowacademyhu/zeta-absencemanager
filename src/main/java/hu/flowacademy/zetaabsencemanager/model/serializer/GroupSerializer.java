package hu.flowacademy.zetaabsencemanager.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hu.flowacademy.zetaabsencemanager.model.Group;
import java.io.IOException;

public class GroupSerializer extends JsonSerializer {

  @Override
  public void serialize(Object o, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    if (o instanceof Group) {
      Group group = (Group) o;
      group.getEmployees().forEach(user -> user.setGroup(null));
      jsonGenerator.writeObject(group);
    }
  }
}