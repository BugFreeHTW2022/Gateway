package htw.controller;

import htw.config.MessagingConfig;
import htw.entity.CurrencyServiceRequest;
import htw.entity.ProductServiceRequest;
import htw.entity.newProduct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/services")
public class RequestPublisher {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private RabbitTemplate template2;

    @PostMapping("/currency/{from}/{to}/{quantity}")
    public Object exchangeCurrency(@RequestBody CurrencyServiceRequest request, @PathVariable String from, @PathVariable String to, @PathVariable String quantity) {
        CurrencyServiceRequest req = new CurrencyServiceRequest(from, to, Double.parseDouble(quantity));

        Object convertedAmount = template.convertSendAndReceive(MessagingConfig.currencyExchange, MessagingConfig.CurrencyMSRoutingKey, req);
        return convertedAmount;
    }

    @PostMapping("/product/createNewProduct")
    public Object createNewProduct(@RequestBody newProduct request) {
        ProductServiceRequest req = new ProductServiceRequest("createProduct", request.convertToMessage());
        Object answer = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return answer;
    }

    @GetMapping("/product/components")
    public Object getComponents() {
        ProductServiceRequest req = new ProductServiceRequest("getComponents", "");
        Object components = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return components;
    }

    @GetMapping("/product/products")
    public Object getProducts() {
        ProductServiceRequest req = new ProductServiceRequest("getProducts", "");
        Object products = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return products;
    }

    @GetMapping("/product/getProduct/{id}")
    public Object getProductDetails(@PathVariable String id) {
        ProductServiceRequest req = new ProductServiceRequest("getProduct", id);
        Object product = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return product;
    }

    @GetMapping("/product/getDefaultProducts")
    public Object getDefaultProducts() {
        ProductServiceRequest req = new ProductServiceRequest("getProductsFromUser", "DEFAULT");
        Object defaultProducts = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return defaultProducts;
    }

    @GetMapping("/product/userProducts/{username}")
    public Object getProductsFromUser(@PathVariable String username) {
        ProductServiceRequest req = new ProductServiceRequest("getProductsFromUser", username);
        Object userProducts = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return userProducts;
    }

    @PostMapping("/product/update/{id}")
    public Object updateProduct(@RequestBody newProduct request, @PathVariable String id) {
        ProductServiceRequest req = new ProductServiceRequest("updateProduct", request.convertToMessage() + "," + id);
        Object answer = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return answer;
    }

    @DeleteMapping("/product/delete/{id}")
    public Object deleteProduct(@PathVariable String id) {
        ProductServiceRequest req = new ProductServiceRequest("deleteProduct", id);
        Object answer = template.convertSendAndReceive(MessagingConfig.productExchange, MessagingConfig.ProductMSRoutingKey, req);
        return answer;
    }


}

