import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void createInitialRestaurantData() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        createInitialRestaurantData();
        restaurant=spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("14:30:00"));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        createInitialRestaurantData();
        restaurant=spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:30:00"));
        boolean isRestaurantOpen = restaurant.isRestaurantOpen();
        assertFalse(isRestaurantOpen);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        createInitialRestaurantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        createInitialRestaurantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        createInitialRestaurantData();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void should_return_the_order_amount_for_the_selected_items() {
        createInitialRestaurantData();
        List<String> selectedItems = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int totalOrderAmount = restaurant.calculateOrderDetails(selectedItems);
        assertEquals(388, totalOrderAmount);
    }
    @Test
    public void should_return_the_0_as_amount_when_no_items_are_selected() {
        createInitialRestaurantData();
        List<String> selectedItems = new ArrayList<>();
        int totalOrderAmount = restaurant.calculateOrderDetails(selectedItems);
        assertEquals(0, totalOrderAmount);
    }
    @Test
    public void should_return_the_new_order_amount_for_the_selected_items_when_a_new_item_is_added() {
        createInitialRestaurantData();
        List<String> selectedItems = Arrays.asList("Sweet corn soup");
        int totalOrderAmountBeforeAddingItem = restaurant.calculateOrderDetails(selectedItems);
        selectedItems = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int totalOrderAmountAfterAddingItem = restaurant.calculateOrderDetails(selectedItems);
        assertEquals(388, totalOrderAmountAfterAddingItem);
        assertEquals(269, totalOrderAmountAfterAddingItem-totalOrderAmountBeforeAddingItem);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}