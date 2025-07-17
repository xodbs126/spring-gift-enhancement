package gift.controller.itemController;


import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemResponseDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.dto.itemDto.ResponseItems;
import gift.entity.Item;
import gift.service.itemService.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ResponseItems> addItem(
            @RequestBody @Valid ItemCreateDto dto
    ) {
        Item item = itemService.saveItem(dto);
        ItemResponseDto responseDto = ItemResponseDto.from(item);

        return new ResponseEntity<>(new ResponseItems(List.of(responseDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseItems> getItems(@RequestParam(required = false) String name, @RequestParam(required = false) Integer price) {
        List<Item> items = itemService.getItems(name, price);
        List<ItemResponseDto> itemList = ItemResponseDto.from(items);

        return ResponseEntity.ok(new ResponseItems(itemList));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItems(
            @RequestParam(required = false) String name
    ) {
        itemService.delete(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseItems> updateItems(
            @PathVariable Long id,
            @RequestBody @Valid ItemUpdateDto dto
    ) {
        Item updatedItem = itemService.updateItem(id, dto);
        ItemResponseDto responseDto = ItemResponseDto.from(updatedItem);

        return ResponseEntity.ok(new ResponseItems(List.of(responseDto)));
    }
}
