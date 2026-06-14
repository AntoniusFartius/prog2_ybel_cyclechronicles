package cyclechronicles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class ShopTest {

    @Test
    void acceptsNormalBikeWhenQueueIsEmpty() {
        Shop shop = new Shop();
        Order order = order(Type.RACE, "Alice");

        boolean accepted = shop.accept(order);

        assertTrue(accepted);
    }

    @Test
    void rejectsGravelBike() {
        Shop shop = new Shop();
        Order order = order(Type.GRAVEL, "Alice");

        boolean accepted = shop.accept(order);

        assertFalse(accepted);
    }

    @Test
    void rejectsEBike() {
        Shop shop = new Shop();
        Order order = order(Type.EBIKE, "Alice");

        boolean accepted = shop.accept(order);

        assertFalse(accepted);
    }

    @Test
    void rejectsOrderWhenCustomerAlreadyHasPendingOrder() {
        Shop shop = new Shop();

        Order firstOrder = order(Type.RACE, "Alice");
        Order secondOrder = order(Type.FIXIE, "Alice");

        assertTrue(shop.accept(firstOrder));

        boolean accepted = shop.accept(secondOrder);

        assertFalse(accepted);
    }

    @Test
    void acceptsOrderWhenThereAreFourPendingOrders() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer 1")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 2")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 4")));

        Order fifthOrder = order(Type.SINGLE_SPEED, "Customer 5");

        boolean accepted = shop.accept(fifthOrder);

        assertTrue(accepted);
    }

    @Test
    void rejectsOrderWhenThereAreAlreadyFivePendingOrders() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer 1")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 2")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 4")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 5")));

        Order sixthOrder = order(Type.FIXIE, "Customer 6");

        boolean accepted = shop.accept(sixthOrder);

        assertFalse(accepted);
    }

    private Order order(Type type, String customer) {
        Order order = mock(Order.class);

        when(order.getBicycleType()).thenReturn(type);
        when(order.getCustomer()).thenReturn(customer);

        return order;
    }
}
