package com.cydeo.lab08rest.service.unit;

import com.cydeo.lab08rest.entity.*;
import com.cydeo.lab08rest.enums.CartState;
import com.cydeo.lab08rest.enums.DiscountType;
import com.cydeo.lab08rest.repository.CartItemRepository;
import com.cydeo.lab08rest.repository.CartRepository;
import com.cydeo.lab08rest.repository.DiscountRepository;
import com.cydeo.lab08rest.repository.ProductRepository;
import com.cydeo.lab08rest.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private DiscountRepository discountRepository;


    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void should_not_add_to_cart_when_product_doesnt_exist(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(
                () -> cartService.addToCart(new Customer(),1L,10)
        );
        assertThat(throwable).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void should_throw_exception_when_product_remaining_quantity_is_less_then(){
        Product product = new Product();
        product.setRemainingQuantity(10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Throwable throwable = catchThrowable(
                () -> cartService.addToCart(new Customer(),1L,15)
        );
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Not enough stock");
    }

    @Test
    public void should_add_item_to_cart_when_cart_is_exists_and_cart_item_exists_in_cart(){
        //Given
        Product product = new Product();
        product.setId(1L);
        product.setRemainingQuantity(10);

        Cart cart = new Cart();
        cart.setCartState(CartState.CREATED);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(2);

        Customer customer = new Customer();
        customer.setId(1L);
        //When
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(),CartState.CREATED))
                .thenReturn(cartList);
        when(cartItemRepository.findAllByCartAndProduct(cart,product)).thenReturn(cartItem);
        //Then
        boolean result = cartService.addToCart(customer,product.getId(),8);
        assertTrue(result);
        assertThat(cartItem.getQuantity()).isEqualTo(10);
    }


    // homework
    // write unit test for scenarios
    // scenario 1 -> cart doesn't exist for customer or cart list is null (new Arraylist or null)
    // scenario 2 -> cart exist but cart item doesn't exist

    // scenario 1
    @Test
    public void should_add_item_to_cart_when_cart_doesnt_exist() {
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setName("Orange");
        product.setPrice(BigDecimal.TEN);
        product.setRemainingQuantity(10);

        Customer customer = new Customer();
        customer.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED)).thenReturn(null);
        when(cartItemRepository.findAllByCartAndProduct(any(), any())).thenReturn(null);

        boolean result = cartService.addToCart(customer, product.getId(), 3);
        assertTrue(result);
    }

    // scenario 2
    @Test
    public void should_add_to_cart_when_cart_exists_and_cart_item_not_exist(){
        Product product = new Product();
        product.setId(1L);
        product.setRemainingQuantity(10);

        Cart cart = new Cart();
        cart.setCartState(CartState.CREATED);


        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        Customer customer = new Customer();
        customer.setId(1L);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState
                (customer.getId(), CartState.CREATED)).thenReturn(cartList);
        when(cartItemRepository.findAllByCartAndProduct(cart, product)).thenReturn(null);


        boolean result = cartService.addToCart(customer, product.getId(), 8);
        assertTrue(result);
    }
    @Test
    public void should_create_new_cart_list_when_cart_list_is_null(){
        //Given
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);

        product.setRemainingQuantity(10);
        Customer customer = new Customer();
        customer.setId(1L);


        //When
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(),CartState.CREATED))
                .thenReturn(null);
        when(cartItemRepository.findAllByCartAndProduct(any(),any())).thenReturn(null);

        //Then
        boolean result = cartService.addToCart(customer,product.getId(),8);
        assertTrue(result);
    }

    @Test
    public void should_create_new_cart_list_when_cart_list_exists_but_cart_list_item_doesnt_exists(){
        //Given
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);

        product.setRemainingQuantity(10);
        Customer customer = new Customer();
        customer.setId(1L);

        List<Cart> cartList = new ArrayList<>();


        //When
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(),CartState.CREATED))
                .thenReturn(cartList);
        when(cartItemRepository.findAllByCartAndProduct(any(),any())).thenReturn(null);

        //Then
        boolean result = cartService.addToCart(customer,product.getId(),8);
        assertTrue(result);
    }


    @Test
    public void should_throw_an_exception_when_cart_list_size_is_two(){
        //Given
        Product product = new Product();
        product.setId(1L);
        product.setRemainingQuantity(10);

        Cart cart = new Cart();
        cart.setCartState(CartState.CREATED);

        Cart cart2 = new Cart();
        cart2.setCartState(CartState.CREATED);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        cartList.add(cart2);

        Customer customer = new Customer();
        customer.setId(1L);
        //When
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(),CartState.CREATED))
                .thenReturn(cartList);

        Throwable throwable = catchThrowable(
                () -> cartService.addToCart(customer,1L,8)
        );
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Duplicate cart count. Check values on database");
    }

    @Test
    public void should_throw_exception_when_discount_not_exists(){
        when(discountRepository.findFirstByName("Dummy discount name")).thenReturn(null);

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount("Dummy discount name",
                                new Cart())
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Discount couldn't find ");
    }

    @Test
    public void should_throw_exception_when_discount_amount_is_null(){
        Discount discount = new Discount();

        when(discountRepository.findFirstByName("Dummy discount name")).thenReturn(discount);

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount("Dummy discount name",
                                new Cart())
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Discount amount can not be null ");
    }

    @Test
    public void should_throw_exception_when_discount_minimum_amount_is_null(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.ZERO);

        when(discountRepository.findFirstByName("Dummy discount name")).thenReturn(discount);

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount("Dummy discount name",
                                new Cart())
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Discount minimum amount can not be null ");
    }

    @Test
    public void should_throw_exception_when_discount_minimum_amount_less_than_one(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.ZERO);
        discount.setMinimumAmount(BigDecimal.ZERO);

        when(discountRepository.findFirstByName("Dummy discount name")).thenReturn(discount);

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount("Dummy discount name",
                                new Cart())
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Discount amount needs be bigger than zero ");
    }

    @Test
    public void should_throw_exception_when_cart_item_list_is_zero(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.TEN);
        discount.setMinimumAmount(BigDecimal.valueOf(100L));
        discount.setName("discount");
        discount.setDiscountType(DiscountType.AMOUNT_BASED);
        Cart cart = new Cart();

        when(discountRepository.findFirstByName(discount.getName())).thenReturn(discount);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(new ArrayList<>());

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount(discount.getName(),
                                cart)
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("There is no item in the cart");
    }

    @Test
    public void should_throw_exception_when_cart_item_list_null(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.TEN);
        discount.setMinimumAmount(BigDecimal.valueOf(100L));
        discount.setName("discount");
        discount.setDiscountType(DiscountType.AMOUNT_BASED);
        Cart cart = new Cart();

        when(discountRepository.findFirstByName(discount.getName())).thenReturn(discount);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(null);

        Throwable throwable = catchThrowable(
                () -> cartService
                        .applyDiscountToCartIfApplicableAndCalculateDiscountAmount(discount.getName(),
                                cart)
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("There is no item in the cart");
    }

    @Test
    public void should_return_zero_discount_when_total_cart_amount_less_than_minimum_amount(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.TEN);
        discount.setMinimumAmount(BigDecimal.valueOf(100L));
        discount.setName("discount");
        discount.setDiscountType(DiscountType.AMOUNT_BASED);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));

        Cart cart = new Cart();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(6);

        when(discountRepository.findFirstByName(discount.getName())).thenReturn(discount);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(List.of(cartItem));

        BigDecimal result = cartService.applyDiscountToCartIfApplicableAndCalculateDiscountAmount(discount.getName(), cart);

        assertThat(result).isEqualTo(BigDecimal.ZERO);
        assertNull(cart.getDiscount());

    }

    @Test
    public void should_return_discount_amount_when_total_cart_amount_grater_than_minimum_amount(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.TEN);
        discount.setMinimumAmount(BigDecimal.valueOf(100));
        discount.setName("discount");
        discount.setDiscountType(DiscountType.AMOUNT_BASED);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(20));

        Cart cart = new Cart();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(6);

        when(discountRepository.findFirstByName(discount.getName())).thenReturn(discount);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(List.of(cartItem));

        BigDecimal result = cartService.applyDiscountToCartIfApplicableAndCalculateDiscountAmount(discount.getName(), cart);

        assertThat(result).isEqualTo(BigDecimal.TEN);
        assertNotNull(cart.getDiscount());

    }

    @Test
    public void should_return_discount_amount_when_total_cart_amount_grater_than_minimum_amount_and_discount_type_is_rate_base(){
        Discount discount = new Discount();
        discount.setDiscount(BigDecimal.TEN);
        discount.setMinimumAmount(BigDecimal.valueOf(100));
        discount.setName("discount");
        discount.setDiscountType(DiscountType.RATE_BASED);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(20));

        Cart cart = new Cart();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(6);

        when(discountRepository.findFirstByName(discount.getName())).thenReturn(discount);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(List.of(cartItem));

        BigDecimal result = cartService.applyDiscountToCartIfApplicableAndCalculateDiscountAmount(discount.getName(), cart);

        assertThat(result).isEqualTo(BigDecimal.valueOf(12));
        assertNotNull(cart.getDiscount());

    }

}
