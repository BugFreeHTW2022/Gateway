package htw.kbe;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, value = "/services")
public class RequestPublisher {
@Autowired
    private RabbitTemplate template;
@Autowired
    private RabbitTemplate template2;
@PostMapping("/currency/{from}/{to}/{quantity}")
public Object exchangeCurrency(@RequestBody CurrencyServiceRequest request, @PathVariable String from, @PathVariable String to, @PathVariable String quantity){
CurrencyServiceRequest req = new CurrencyServiceRequest(from,to,Double.parseDouble(quantity));
    System.out.println(req.getFrom()+req.getTo()+req.getQuantity());
//template.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.ROUTING_KEY,response);
    Object a = template.convertSendAndReceive(MessagingConfig.currencyExchange,MessagingConfig.CurrencyMSRoutingKey,req);
    System.out.println(a);
return a;
}

    @PostMapping("/product/createNewProduct")
    public Object createNewProduct(@RequestBody newProduct request){
    ProductServiceRequest req = new ProductServiceRequest("createProduct",request.convertToMessage());
    Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
    System.out.println(b);
    return b;
    }

    @GetMapping("/product/components")
    public Object getComponents(){
        ProductServiceRequest req = new ProductServiceRequest("getComponents","");
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }

    @GetMapping("/product/products")
    public Object getProducts(){
        ProductServiceRequest req = new ProductServiceRequest("getProducts","");
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }

    @GetMapping("/product/getProduct/{id}")
    public Object getProductDetails(@PathVariable String id){
        ProductServiceRequest req = new ProductServiceRequest("getProduct",id);
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }

    @GetMapping("/product/getDefaultProducts")
    public Object getDefaultProducts(){
        ProductServiceRequest req = new ProductServiceRequest("getProductsFromUser","DEFAULT");
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }
    @GetMapping("/product/userProducts/{username}")
    public Object getProductsFromUser(@PathVariable String username){
        ProductServiceRequest req = new ProductServiceRequest("getProductsFromUser",username);
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }

    @PostMapping("/product/update/{id}")
    public Object updateProduct(@RequestBody newProduct request, @PathVariable String id){
        ProductServiceRequest req = new ProductServiceRequest("updateProduct",request.convertToMessage()+","+id);
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }

    @DeleteMapping("/product/delete/{id}")
    public Object deleteProduct(@PathVariable String id){
        ProductServiceRequest req = new ProductServiceRequest("deleteProduct",id);
        Object b = template.convertSendAndReceive(MessagingConfig.productExchange,MessagingConfig.ProductMSRoutingKey,req);
        System.out.println(b);
        return b;
    }






}

