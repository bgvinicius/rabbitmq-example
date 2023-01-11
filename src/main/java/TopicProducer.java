import com.github.javafaker.Faker;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicProducer {
    private static final String EXCHANGE_NAME = "credit-cards";

    public static void main(String[] argv) throws Exception {
        var faker = new Faker();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            for (int i = 0; i < 100; i++) {
                // dankort, laser, switch, forbrugsforeningen, discover, maestro, visa, mastercard.. etc
                String routingKey = faker.business().creditCardType();
                String message = Integer.valueOf(i) + " - " + faker.business().creditCardNumber();

                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            }
        }
    }
}
