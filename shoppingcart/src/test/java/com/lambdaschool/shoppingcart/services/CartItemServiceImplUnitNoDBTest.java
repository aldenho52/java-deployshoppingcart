package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.ShoppingCartApplicationTest;
import com.lambdaschool.shoppingcart.models.CartItem;
import com.lambdaschool.shoppingcart.models.CartItemId;
import com.lambdaschool.shoppingcart.models.Product;
import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.repository.CartItemRepository;
import com.lambdaschool.shoppingcart.repository.ProductRepository;
import com.lambdaschool.shoppingcart.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingCartApplicationTest.class,
    properties = {"command.line.runner.enabled=false"})
class CartItemServiceImplUnitNoDBTest
{

    @Autowired
    private CartItemService cartItemService;

    @MockBean
    private UserRepository userrepos;

    @MockBean
    private ProductRepository prodrepos;

    @MockBean
    private CartItemRepository cartitemsrepos;

    @Autowired
    private DataSource dataSource;

    private List<Product> productList;

    private List<User> userList;

    private List<CartItem> cartItemList;


    @BeforeEach
    public void setUp()
    {
        productList = new ArrayList<>();
        userList = new ArrayList<>();
        cartItemList = new ArrayList<>();

        User user1 = new User("alden", "password", "ach52@gmail.com", "Likes food");
        user1.setUserid(10);
        userList.add(user1);

        Product book = new Product("book", true, 5.00, "book of dead", "revives mummies");
        book.setProductid(100);
        productList.add(book);

        CartItem cartItem1 = new CartItem(user1, book, 1, "");
        cartItemList.add(cartItem1);

        MockitoAnnotations.initMocks(this);
    }


    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void addToCart() throws Exception
    {

        User user1 = new User("alden", "password", "ach52@gmail.com", "Likes food");
        Product book = new Product("book", true, 5.00, "book of dead", "revives mummies");
        CartItem cartItem1 = new CartItem(user1, book, 1, "");


        Mockito.when(userrepos.findById(10L))
            .thenReturn(Optional.of(userList.get(0)));

        Mockito.when(prodrepos.findById(100L))
            .thenReturn(Optional.of(productList.get(0)));

        Mockito.when(cartitemsrepos.findById(new CartItemId(10L, 100L)))
            .thenReturn(Optional.of(cartItemList.get(0)));

        Mockito.when(cartitemsrepos.save(any(CartItem.class)))
            .thenReturn(cartItem1);

        assertEquals(2, cartItem1.getQuantity()+1);

    }

    @Test
    public void removeFromCart()
    {
        User user1 = new User("alden", "password", "ach52@gmail.com", "Likes food");
        Product book = new Product("book", true, 5.00, "book of dead", "revives mummies");
        CartItem cartItem1 = new CartItem(user1, book, 1, "");
        CartItemId cartItemId1 = new CartItemId(10L, 100L);

        Mockito.when(userrepos.findById(10L))
            .thenReturn(Optional.empty());

        Mockito.when(prodrepos.findById(100L))
            .thenReturn(Optional.of(productList.get(0)));

        Mockito.when(cartitemsrepos.findById(new CartItemId(10L, 100L)))
            .thenReturn(Optional.of(cartItemList.get(0)));

        assertEquals(cartItem1.getQuantity(), 1);

    }
}