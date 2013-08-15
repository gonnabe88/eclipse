

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PointAdapter extends TypeAdapter<Point> {
	public void main(String args[])
	{
		Gson gson = new Gson();
		JsonWriter writer;
		P
		write(writer.value("11"),);
		
	}
    public Point read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String xy = reader.nextString();
      String[] parts = xy.split(",");
      int x = Integer.parseInt(parts[0]);
      int y = Integer.parseInt(parts[1]);
      return new Point(x, y);
    }
    public void write(JsonWriter writer, Point value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      String xy = value.getX() + "," + value.getY();
      writer.value(xy);
    }
}
