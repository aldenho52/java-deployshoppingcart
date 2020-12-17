package com.lambdaschool.shoppingcart.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.shoppingcart.ShoppingCartApplicationTest;
import com.lambdaschool.shoppingcart.models.CartItem;
import com.lambdaschool.shoppingcart.models.Product;
import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.repository.UserRepository;
import com.lambdaschool.shoppingcart.services.CartItemService;
import com.lambdaschool.shoppingcart.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ShoppingCartApplicationTest.class,
    properties = {
        "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class CartControllerTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userrepos;

    private List<Product> productList;
    private List<User> userList;
    private List<CartItem> cartItemList;

    @Before
    public void setUp() throws Exception
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

        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem1);
        user1.setCarts(cartItems);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listCartItemsByUserId() throws Exception
    {
        String apiUrl = "/carts/user";
        Mockito.when(userService.findByName(any(String.class)))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println(tr);
        assertEquals(er,
            tr);
    }

    @Test
    public void addToCart() throws Exception
    {
        String apiUrl = "/carts/add/product/100/bacon";
        Mockito.when(userService.findByName(any(String.class)))
            .thenReturn(userList.get(0));

        Mockito.when(cartItemService.addToCart(any(Long.class), any(Long.class), any(String.class)))
            .thenReturn(cartItemList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(cartItemList.get(0));

        System.out.println(tr);
        assertEquals(er,
            tr);
    }

    @Test
    public void removeFromCart()
    {
    }
}