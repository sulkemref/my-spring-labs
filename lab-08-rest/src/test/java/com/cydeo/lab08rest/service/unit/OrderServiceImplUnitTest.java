package com.cydeo.lab08rest.service.unit;

import com.cydeo.lab08rest.entity.*;
import com.cydeo.lab08rest.enums.CartState;
import com.cydeo.lab08rest.enums.PaymentMethod;
import com.cydeo.lab08rest.repository.*;
import com.cydeo.lab08rest.service.CartService;
import com.cydeo.lab08rest.service.impl.OrderServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void should_throw_exception_when_the_customer_does_not_exist(){
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(
                () -> orderService.placeOrder(PaymentMethod.CREDIT_CARD,1L,1L)
        );

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Customer couldn't find");
    }

    @Test
    public void should_throw_exception_when_cart_list_of_the_customer_is_null(){
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED))
                .thenReturn(null);
        Throwable throwable = catchThrowable(
                () -> orderService.placeOrder(PaymentMethod.CREDIT_CARD,1L,1L)
        );
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Cart couldn't find or cart is empty");

    }

    @Test
    public void should_throw_exception_when_cart_list_of_the_customer_size_is_zero(){
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED))
                .thenReturn(new ArrayList<>());
        Throwable throwable = catchThrowable(
                () -> orderService.placeOrder(PaymentMethod.CREDIT_CARD,1L,1L)
        );
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Cart couldn't find or cart is empty");
    }

    @Test
    public void should_place_order_without_discount(){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setRemainingQuantity(60);

        Customer customer = new Customer();
        customer.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(4);
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED))
                .thenReturn(cartList);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(cartItemList);

        BigDecimal result = orderService.placeOrder(PaymentMethod.TRANSFER,cart.getId(),customer.getId());

        assertThat(result).isEqualTo(BigDecimal.valueOf(20));
        assertThat(product.getRemainingQuantity()).isEqualTo(56);

    }

    @Test
    public void should_place_order_with_discount(){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setRemainingQuantity(60);

        Customer customer = new Customer();
        customer.setId(1L);

        Discount discount = new Discount();
        discount.setName("discount");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setDiscount(discount);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(4);
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED)).thenReturn(cartList);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(cartItemList);
        when(cartService.applyDiscountToCartIfApplicableAndCalculateDiscountAmount(cart.getDiscount().getName(),
                cart)).thenReturn(BigDecimal.TEN);

        BigDecimal result = orderService.placeOrder(PaymentMethod.TRANSFER,cart.getId(),customer.getId());

        assertThat(result).isEqualTo(BigDecimal.valueOf(10));
        assertThat(product.getRemainingQuantity()).isEqualTo(56);

    }

    @Test
    public void should_place_order_with_discount_and_credit_card_payment(){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setRemainingQuantity(60);

        Customer customer = new Customer();
        customer.setId(1L);

        Discount discount = new Discount();
        discount.setName("discount");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setDiscount(discount);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED)).thenReturn(cartList);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(cartItemList);
        when(cartService.applyDiscountToCartIfApplicableAndCalculateDiscountAmount(cart.getDiscount().getName(),
                cart)).thenReturn(BigDecimal.valueOf(15));

        BigDecimal result = orderService.placeOrder(PaymentMethod.CREDIT_CARD,cart.getId(),customer.getId());

        assertThat(result).isEqualTo(BigDecimal.valueOf(25));
        assertThat(product.getRemainingQuantity()).isEqualTo(50);
    }

    @Test
    public void should_not_place_order_because_item_is_removed_from_cart(){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setRemainingQuantity(5);

        Customer customer = new Customer();
        customer.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED)).thenReturn(cartList);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(cartItemList);

        BigDecimal result = orderService.placeOrder(PaymentMethod.CREDIT_CARD,cart.getId(),customer.getId());

        assertThat(result).isEqualTo(BigDecimal.ZERO);
        assertThat(product.getRemainingQuantity()).isEqualTo(5);


    }

    @Test
    public void should_place_order_one_product_second_remove_by_quantity(){
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(5));
        product1.setRemainingQuantity(5);

        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(5));
        product2.setRemainingQuantity(2);

        Customer customer = new Customer();
        customer.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);

        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(5);
        cartItem1.setProduct(product1);
        cartItem1.setCart(cart);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(5);
        cartItem2.setProduct(product2);
        cartItem2.setCart(cart);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cartRepository.findAllByCustomerIdAndCartState(customer.getId(), CartState.CREATED)).thenReturn(cartList);
        when(cartItemRepository.findAllByCart(cart)).thenReturn(cartItemList);

        BigDecimal result = orderService.placeOrder(PaymentMethod.CREDIT_CARD,cart.getId(),customer.getId());

        assertThat(result).isEqualTo(BigDecimal.valueOf(15));
        assertThat(product1.getRemainingQuantity()).isEqualTo(0);
        assertThat(product2.getRemainingQuantity()).isEqualTo(2);

    }
}
