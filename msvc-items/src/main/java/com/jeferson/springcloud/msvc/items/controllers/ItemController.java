package com.jeferson.springcloud.msvc.items.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.models.ProductDto;
import com.jeferson.springcloud.msvc.items.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;
    private final CircuitBreakerFactory cBreakerFactory;

    // Inyectando para ver la configuracion en el properties
    @Value("${configuracion.texto}")
    private String texto;

    // Inyectando el ambiente de dev properties
    @Autowired
    private Environment env;

    public ItemController(ItemService itemService, CircuitBreakerFactory cBreakerFactory) {
        this.itemService = itemService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port){
        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("port", port);
        logger.info(texto);
        logger.info(port);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.name", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping
    public List<ItemDto> list() {
        return itemService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) throws InterruptedException {

        Optional<ItemDto> optItem = cBreakerFactory.create("items").run(() -> itemService.findById(id), e -> {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            ProductDto product = new ProductDto();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Camara Sony");
            product.setPrice(1250.00);
            return Optional.of(new ItemDto(product, 5));
        });

        if (optItem.isPresent()) {
            return ResponseEntity.ok(optItem.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                        "message", "No existe el producto en el micorservicio msvc-products"));
    }

    // La anotacion circuiteBreaker es compatible con el application yml no con la
    // forma programatica
    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct")
    @GetMapping("/details/{id}")
    public ResponseEntity<?> details2(@PathVariable Long id) throws InterruptedException {

        Optional<ItemDto> optItem = itemService.findById(id);
        if (optItem.isPresent()) {
            return ResponseEntity.ok(optItem.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                        "message", "No existe el producto en el micorservicio msvc-products"));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct2")
    @TimeLimiter(name = "items")
    @GetMapping("/details2/{id}")
    public CompletableFuture<?> details3(@PathVariable Long id) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {

            Optional<ItemDto> optItem = itemService.findById(id);
            if (optItem.isPresent()) {
                return ResponseEntity.ok(optItem.get());
            }
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap(
                            "message", "No existe el producto en el micorservicio msvc-products"));
        });
    }

    public ResponseEntity<?> getFallBackMethodProduct(Throwable e) {
        System.out.println(e.getMessage());
        logger.error(e.getMessage());
        ProductDto product = new ProductDto();
        product.setCreateAt(LocalDate.now());
        product.setId(1L);
        product.setName("Camara Sony");
        product.setPrice(1250.00);
        return ResponseEntity.ok(new ItemDto(product, 5));
    }

    public CompletableFuture<?> getFallBackMethodProduct2(Throwable e) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            ProductDto product = new ProductDto();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Camara Sony");
            product.setPrice(1250.00);
            return ResponseEntity.ok(new ItemDto(product, 5));
        });
    }
}