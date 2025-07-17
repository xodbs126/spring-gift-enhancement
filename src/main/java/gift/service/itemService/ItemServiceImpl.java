package gift.service.itemService;

import gift.dto.itemDto.ItemCreateDto;
import gift.dto.itemDto.ItemUpdateDto;
import gift.entity.Item;
import gift.repository.itemRepository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public Item saveItem(ItemCreateDto dto) {
        Item item = new Item(dto.name(), dto.price(), dto.imageUrl());

        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItems(String name, Integer price) {
        if (name == null && price == null) {
            return getAllItems();
        }
        if (name == null) {
            return itemRepository.findByPrice(price);
        }
        if (price == null) {
            itemRepository.findByName(name);
        }
        return itemRepository.findByNameAndPrice(name, price);
    }

    @Override
    @Transactional
    public void delete(String name) {
        Item targetItem = itemRepository.findByName(name);
        itemRepository.delete(targetItem);
    }

    @Override
    @Transactional
    public Item updateItem(Long id, ItemUpdateDto dto) {
        Optional<Item> targetItem = findItemById(id);

        Item item = targetItem.get();

        String name = dto.name();
        Integer price = dto.price();
        String imageUrl = dto.imageUrl();

        item.update(name, price, imageUrl);

        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> findItemByName(String name) {
        return Optional.ofNullable(itemRepository.findByName(name));
    }

    @Override
    public Optional<Item> findItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
