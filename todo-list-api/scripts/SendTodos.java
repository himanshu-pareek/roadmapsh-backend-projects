import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SendTodos {

    private static final String AUTH_TOKEN = "841c3f75-6434-4c13-a044-65038e20eaf6";
    private static final String API_URL = "http://localhost:8080/todos";
    private static final String TODO_FILE = "sample-todos.txt";

    public static void main(String[] args) {
        List<Todo> todos = readTodosFromFile(TODO_FILE);
        for (Todo todo : todos) {
            sendTodo(todo.title, todo.description);
        }
    }

    private static List<Todo> readTodosFromFile(String filePath) {
        List<Todo> todos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String title;
            while ((title = reader.readLine()) != null) {
                title = title.trim();
                if (title.isEmpty()) continue; // skip blank lines
                String description = reader.readLine();
                if (description != null) {
                    todos.add(new Todo(title, description.trim()));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read todos: " + e.getMessage());
            e.printStackTrace();
        }
        return todos;
    }

    private static void sendTodo(String title, String description) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
            conn.setDoOutput(true);

            String jsonPayload = String.format("{\"title\": \"%s\", \"description\": \"%s\"}",
                                               escapeJson(title), escapeJson(description));

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Sent: " + title + " - Status: " + responseCode);
            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Failed to send todo: " + title);
            e.printStackTrace();
        }
    }

    private static String escapeJson(String value) {
        return value.replace("\"", "\\\"");
    }

    // Simple class to represent a todo
    static class Todo {
        String title;
        String description;

        Todo(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}
