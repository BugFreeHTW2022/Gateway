package htw.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class newProduct {
    String name;
    String size;
    String teig;
    String fill;
    String glasur;
    String description;
    String extras;
    String userName;


    public String convertToMessage() {
        String message = this.name + "," + this.size + "," + this.teig + "," + this.fill + "," + this.glasur + "," + this.description + "," + this.extras + "," + this.userName;
        return message;
    }
}


