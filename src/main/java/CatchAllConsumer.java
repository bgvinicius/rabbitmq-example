import java.util.List;

public class CatchAllConsumer {
    public static void main(String[] args) throws Exception {
        new TopicConsumer(List.of("*")).consume();
    }
}
