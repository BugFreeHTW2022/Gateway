package htw;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;






@Configuration
public class MessagingConfig {

    public static final String CurrencyMSQueueName = "CurrencyMSQueue";
    public static final String ProductMSQueueName = "ProductMSQueue";
    public static final String PriceMSQueueName = "PriceMSQueue";

    public static final String productExchange = "productExchange";
    public static final String currencyExchange = "currencyExchange";
    public static final String priceExchange = "priceExchange";
    public static final String CurrencyMSRoutingKey = "CurrencyMSRoutingKey";
    public static final String ProductMSRoutingKey = "ProductMSRoutingKey";
    public static final String PriceMSRoutingKey = "PriceMSRoutingKey";


    @Bean
    public Queue currencyMSQueue(){
        return new Queue(CurrencyMSQueueName);
    }

    @Bean
    public Queue ProductMSQueue(){
        return new Queue(ProductMSQueueName);
    }

    @Bean
    public Queue PriceMSQueue(){
        return new Queue(PriceMSQueueName);
    }

    @Bean
    public DirectExchange CurrExchange(){
        return new DirectExchange(currencyExchange);
    }
    @Bean
    public DirectExchange ProductExchange(){
        return new DirectExchange(productExchange);
    }
    @Bean
    public DirectExchange PriceExchange(){
        return new DirectExchange(priceExchange);
    }

    @Bean
    public Binding bindingPriceMS(){
        return BindingBuilder.bind(PriceMSQueue()).to(PriceExchange()).with(PriceMSRoutingKey);
    }
    @Bean
    public Binding bindingCurrencyMS(){
        return BindingBuilder.bind(currencyMSQueue()).to(CurrExchange()).with(CurrencyMSRoutingKey);
    }

    @Bean
    public Binding bindingProductMS(){
        return BindingBuilder.bind(ProductMSQueue()).to(ProductExchange()).with(ProductMSRoutingKey);
    }


    @Bean
    public MessageConverter converter(){

        return new Jackson2JsonMessageConverter();
    }




    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }






}


/*@Configuration
public class MessagingConfig {

    public static final String QUEUE = "curr";
    public static final String EXCHANGE = "curr";
    public static final String ROUTING_KEY = "curr";
    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean

    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    public MessageConverter converter(){

        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }




}*/
