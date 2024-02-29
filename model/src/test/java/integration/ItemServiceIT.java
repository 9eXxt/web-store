package integration;

import dao.ItemDao;
import dto.ItemReadDto;
import integration.util.ConnectionTestUtil;
import integration.util.TestDataImporter;
import mapper.ItemReadMapper;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import service.ItemService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemServiceIT {
    private ItemService itemService;
    private static final SessionFactory sessionFactory = ConnectionTestUtil.buildSessionFactory();

    @BeforeAll
    static void init() {
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void setUp() {
        ItemDao itemDao = new ItemDao();
        ItemReadMapper itemReadMapper = new ItemReadMapper();
        itemService = new ItemService(itemDao, itemReadMapper, sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @Test
    @DisplayName("Find all items in database")
    void findAll_WithValidData_ReturnsListOfItemsDto() {
        List<ItemReadDto> itemsDtoList = itemService.findAll();

        assertThat(itemsDtoList).isNotEmpty()
                .hasSize(2);
    }
}
